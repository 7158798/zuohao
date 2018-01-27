package com.base.service.job;

import com.base.common.sms.client.SmsDayuUtils;
import com.base.common.sms.facade.response.SmsResultResp;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.configs.BusinessConfig;
import com.jucaifu.common.util.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuxun on 16-8-25.
 */
@Component
public class InfoExchangeJob implements JobConstant {

    private static final Logger logger = LoggerFactory.getLogger(InfoExchangeJob.class);
    /**
     * 定时任务同步汇率数据,每小时同步一次
     */
    @Scheduled(cron = "0 8 * * * ? ")
    //@Scheduled(cron = "0 0/3 * * * ? ")
    public void syncExchange(){
        if (isJobClose()){
            return;
        }
        logger.info("同步货币汇率开始： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        boolean flag = false;
        // 错误信息集合
        List<String> error = new ArrayList<>();
        try{
            // 同步汇率
            //error = BaseServiceBizPool.getInstance().infoExchangeConsoleFacade.syncExchange();
            BaseServiceBizPool.getInstance().infoExchangeBiz.syncExchange();
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            // 发生异常
            flag = true;
        }
        String context = null;
        if (flag){
            context = "同步汇率发生异常";
        } else if(error == null){
            context = "未设置需要同步的币种";
        } else if(error.size() > 0){
            context = "同步以下币种失败：" + error.toString();
        }
        if (StringUtils.isNotBlank(context)){
            // 同步数据发生异常，发送短信通知相关人员
            logger.info("同步货币汇率发生异常，发送短信：" + context);
            SmsResultResp resp =  SmsDayuUtils.sendSms(BusinessConfig.INFO_FAIL_PHONENUMBER, context);
            if (!resp.isResult()) {
                logger.error("同步货币汇率发生异常，发送短信通知发送失败", BusinessConfig.INFO_FAIL_PHONENUMBER);
            }

        }
        logger.info("同步货币汇率结束： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }

    /**
     * 定时任务删除已经过期的数据,早上1.10执行
     */
    //@Scheduled(cron = "0 10 1 * * ? ")
    public void deleteOverdue(){

        logger.info("删除过期外汇汇率开始： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        // 获取过期的外汇数据（前天非发布状态的数据）
        List<InfoExchange> list = BaseServiceBizPool.getInstance().infoExchangeBiz.getExchangeByOverdue();
        try {
            for (InfoExchange exchange : list){
                BaseServiceBizPool.getInstance().infoExchangeBiz.deleteExchange(exchange.getUuid());
            }
        } catch (Exception e){
            logger.error("删除过期外汇汇率失败" + e.getMessage(),e);
        }
        logger.info("删除过期外汇汇率结束： " + DateHelper.date2String(new Date(),DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }

}
