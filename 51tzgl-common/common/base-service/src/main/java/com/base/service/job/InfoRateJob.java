package com.base.service.job;

import com.base.facade.info.enums.InfoRateDataSource;
import com.base.facade.info.pojo.po.InfoBank;
import com.base.facade.info.pojo.po.InfoRate;
import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.utils.BaseRateUtil;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.util.DateHelper;
import com.jucaifu.common.util.GroupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxun on 16-8-30.
 */
@Component
public class InfoRateJob implements JobConstant {

    private static final Logger logger = LoggerFactory.getLogger(InfoRateJob.class);

    /**
     * 同步银行利率,1.20
     */
    @Scheduled(cron = "0 20 1 * * ? ")
    // @Scheduled(cron = "0 0/10 * * * ? ")
    private void syncRate(){

        if (isJobClose()){
            return;
        }

        logger.info("同步银行利率开始： " + DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        try{
            // 获取所有的银行
            List<InfoBank> list = BaseServiceBizPool.getInstance().infoBankBiz.selectAll();
            if (list != null && list.size() > 0){
                Map<Object,InfoBank> map = GroupHelper.groupByFieldName(InfoBank.class,"bankName",list);
                for (InfoRateDataSource dataSource : InfoRateDataSource.values()){
                    logger.info("同步利率开始：" + dataSource.getBankName());
                    InfoBank bank = map.get(dataSource.getBankName());
                    if (bank == null){
                        logger.info(dataSource.getBankName() + " 没有对应的银行信息，终止该银行同步数据");
                        continue;
                    }
                    InfoRate infoRate = BaseRateUtil.getInfoRate(dataSource.getType().getCode(), bank.getUuid());
                    List<InfoRateDetail> detailList = this.parse(dataSource);
                    if (detailList == null || detailList.size() == 0){
                        logger.info("同步利率失败,终止本次操作：" + dataSource.getBankName());
                        continue;
                    }
                    BaseServiceBizPool.getInstance().infoRateBiz.addInfoRate(infoRate,detailList);
                    logger.info("同步利率结束：" + dataSource.getBankName());
                }
            } else {

                logger.info("没有银行数据，终止同步利率");
            }

        } catch (Exception e) {
            logger.error("同步银行利率失败:" + e.getMessage(),e);
        }
        logger.info("同步银行利率结束： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }

    /**
     * 解析利率信息开始
     * @param dataSource
     * @return
     */
    private List<InfoRateDetail> parse(InfoRateDataSource dataSource){

        Class calss;
        Method method;
        Object object;
        try {
            calss = Class.forName(dataSource.getClassName());
            method = calss.getMethod(dataSource.getMethod(),InfoRateDataSource.class);
            object = method.invoke(null, dataSource);

            return (List<InfoRateDetail>) object;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }
}
