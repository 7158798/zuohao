package com.base.service.info.biz;

import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.info.enums.ExchangeConstant;
import com.base.facade.info.enums.InfoExchangeSource;
import com.base.facade.info.enums.InfoRateGainWay;
import com.base.facade.info.enums.InfoRateStatus;
import com.base.facade.info.pojo.bean.InfoExchangeDetailRespBean;
import com.base.facade.info.pojo.bean.InfoExchangeRespBean;
import com.base.facade.info.pojo.po.InfoCurrency;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.pojo.vo.InfoExchangeVo;
import com.base.service.info.dao.InfoExchangeMapper;
import com.base.service.info.utils.InfoExchangeImportExcelUtil;
import com.base.service.info.utils.InfoExchangeUtil;
import com.base.service.pool.BaseServiceBizPool;
import com.jucaifu.common.dfs.FastDFSHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.poi.WDWUtil;
import com.jucaifu.common.util.GroupHelper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuxun on 16-8-22.
 */
@Service
@Transactional
public class InfoExchangeBiz extends AbsBaseBiz<InfoExchange,InfoExchangeVo,InfoExchangeMapper> {

    @Autowired
    private InfoExchangeMapper infoExchangeMapper;
    @Override
    public InfoExchangeMapper getDefaultMapper() {
        return infoExchangeMapper;
    }

    /**
     * 获取货币外汇分页集合
     * @param vo
     * @return
     */
    public InfoExchangeVo getExchangePage(InfoExchangeVo vo){

        List<InfoExchange> list = infoExchangeMapper.getByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * 发布货币外汇
     * @param exchangeId
     */
    @Transactional(rollbackFor = Exception.class,readOnly = false)
    public void pushExchange(String exchangeId){

        LOG.dStart(this,"发布货币外汇数据开始");
        if(StringUtils.isBlank(exchangeId)){
            throw new BaseCommonBizException("货币外汇ID不能为空");
        }

        InfoExchange exchange = select(exchangeId);
        if (exchange == null){
            throw new BaseCommonBizException("货币外汇不存在");
        }
        if (StringUtils.equals(exchange.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
            // 状态已发布
            throw new BaseCommonBizException("当前的状态是已发布，不需要重复发布");
        }
        // 已发布状态的数据更新为已失效
        infoExchangeMapper.updateExchangeStatusByPush();
        // 更新为已发布
        exchange.setStatus(InfoRateStatus.FINISH_RELEASE.getCode());
        //
        exchange.setPushTime(new Date());
        infoExchangeMapper.update(exchange);
        LOG.dEnd(this, "发布货币外汇数据结束");
    }

    /**
     * 下架货币外汇
     * @param exchangeId
     */
    public void offExchange(String exchangeId){

        LOG.dStart(this,"下架货币外汇数据开始");
        if(StringUtils.isBlank(exchangeId)){
            throw new BaseCommonBizException("货币外汇ID不能为空");
        }

        InfoExchange exchange = select(exchangeId);
        if (exchange == null){
            throw new BaseCommonBizException("货币外汇不存在");
        }
        if (!StringUtils.equals(exchange.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
            // 状态已发布
            throw new BaseCommonBizException("当前状态非发布状态，不能下架");
        }
        // 更新为待发布
        exchange.setStatus(InfoRateStatus.READY_RELEASE.getCode());
        exchange.setModifedDatetime(new Date());
        infoExchangeMapper.update(exchange);
        LOG.dEnd(this,"下架货币外汇数据结束");
    }

    /**
     * 删除货币外汇数据
     * @param exchangeId 货币外汇ID
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteExchange(String exchangeId){

        LOG.dStart(this,"删除货币外汇数据开始" + exchangeId);

        if(StringUtils.isBlank(exchangeId)){
            throw new BaseCommonBizException("货币外汇ID不存在");
        }
        InfoExchange exchange = select(exchangeId);
        if (exchange == null){
            throw new BaseCommonBizException("货币外汇不存在");
        }
        if (StringUtils.equals(exchange.getStatus(),InfoRateStatus.FINISH_RELEASE.getCode())){
            // 当前已发布的不能删除
            throw new BaseCommonBizException("当前已发布的汇率不能删除");
        }
        // 删除货币外汇
        delete(exchangeId);
        // 删除货币外汇详细数据
        BaseServiceBizPool.getInstance().infoExchangeDetailBiz.deleteExchangeDetail(exchangeId);

        LOG.dEnd(this,"删除货币外汇数据结束" + exchangeId);
    }

    /**
     *
     * @param infoExchange
     * @param list
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void addExchange(InfoExchange infoExchange,List<InfoExchangeDetail> list){

        LOG.dStart(this,"添加货币外汇数据开始");
        validateExchange(infoExchange);
        if (list == null || list.size() == 0){
            throw new BaseCommonBizException("请求参数不能为空");
        }
        Date date = new Date();
        infoExchange.setCreateDatetime(date);
        infoExchange.setModifedDatetime(date);
        infoExchange.setDeleted(false);

        this.insert(infoExchange);

        String exchangeId = infoExchange.getUuid();
        LOG.dStart(this,"添加货币外汇明细开始");
        for (InfoExchangeDetail detail : list){
            detail.setExchangeId(exchangeId);
            detail.setCreateDatetime(date);
            detail.setModifedDatetime(date);
            BaseServiceBizPool.getInstance().infoExchangeDetailBiz.addExchangeDetail(detail);
        }
        LOG.dEnd(this,"添加货币外汇明细结束");
    }


    /**
     * 校验必填字段
     * @param infoExchange
     */
    private void validateExchange(InfoExchange infoExchange){
        if (infoExchange == null){
            throw new BaseCommonBizException("请求参数不能为空");
        }
        if (StringUtils.isBlank(infoExchange.getSource())){
            throw new BaseCommonBizException("数据来源不能为空");
        }
        if (StringUtils.isBlank(infoExchange.getStatus())){
            throw new BaseCommonBizException("当前状态不能为空");
        }
        if (StringUtils.isBlank(infoExchange.getGainWay())){
            throw new BaseCommonBizException("获取方式不能为空");
        }
    }


    /**
     * 通过接口同步货币汇率
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public List<String> syncExchange(){
        LOG.dStart(this,"通过接口同步货币汇率开始");
        // 需要获取汇率的币种
        List<InfoCurrency> curList = BaseServiceBizPool.getInstance().infoCurrencyBiz.getCurrencyListByGainType("1");

        if (curList == null || curList.size() == 0){
            LOG.i(this,"需要获取汇率的币种为空");
            return null;
        }

        // 组装汇率信息表
        InfoExchange exchange = new InfoExchange();
        exchange.setGainWay(InfoRateGainWay.INSERT_BY_INTERFACE.getCode());
        exchange.setStatus(InfoRateStatus.READY_RELEASE.getCode());
        exchange.setSource(InfoExchangeSource.ZG_BACK.getKey());
        List<InfoExchangeDetail> detailList = new ArrayList<>();
        List<String> error = new ArrayList<>();
        for (InfoCurrency currency : curList){
            String result = InfoExchangeUtil.getExchangeDetail(currency.getCurrencyNo());
            if (StringUtils.isBlank(result)){
                // 货币信息为空的场合
                error.add(currency.getCurrencyNo());
                continue;
            }
            InfoExchangeRespBean respBean = JsonHelper.jsonStr2Obj(result,InfoExchangeRespBean.class);
            InfoExchangeDetail detail = new InfoExchangeDetail();
            InfoExchangeDetailRespBean.copyInfoExchangeDetail(respBean.getBOC(), detail);
            detail.setCurrencyName(currency.getCurrencyName());
            detail.setCurrencyNo(currency.getCurrencyNo());
            detailList.add(detail);
        }

        if(detailList != null && detailList.size() > 0){
            // 同步到汇率数据后才新增
            addExchange(exchange,detailList);
        }

        LOG.dEnd(this, "通过接口同步货币汇率结束");

        return error;
    }


    /**
     * 货币兑换计算方法
     * @param sCurNo    持有货币
     * @param tCurNo    兑换货币
     * @param amount    持有金额
     * @return
     */
    public BigDecimal calcExchangeRate(String sCurNo,String tCurNo,BigDecimal amount){

        if (StringUtils.isBlank(sCurNo)){
            throw new BaseCommonBizException("持有货币不能为空");
        }
        if(StringUtils.isBlank(tCurNo)){
            throw new BaseCommonBizException("兑换货币不能为空");
        }
        if (amount == null){
            throw new BaseCommonBizException("持有金额不能为空");
        }
        if (StringUtils.equals(sCurNo,tCurNo)){
            return amount;
        }
        if (StringUtils.equals(sCurNo, ExchangeConstant.CUR_CNY)){

            amount = cnyToAll(tCurNo, amount,Boolean.FALSE);
        } else {
            amount = allToCny(sCurNo,tCurNo,amount);
        }
        return  amount.setScale(4,BigDecimal.ROUND_DOWN);
    }

    /**
     * 人民币兑换其他
     * @param curNo         兑换货币
     * @param amount        兑换的金额
     * @param bankFlag      true使用中行折算价格
     * @return
     */
    private BigDecimal cnyToAll(String curNo,BigDecimal amount,Boolean bankFlag){

        InfoExchangeDetail detail = BaseServiceBizPool.getInstance().infoExchangeDetailBiz.getExchangeDetailByCurNo(curNo);

        if(detail == null){
            throw new BaseCommonBizException("币种" + curNo + "未发布或不存在汇率");
        }
        BigDecimal rate = detail.getmSellPri();
        if (bankFlag || rate == null || rate.compareTo(BigDecimal.ZERO) != 1){
            rate = detail.getBankConversionPri();
        }
        rate = detail.getTradeUnit().divide(rate,4,BigDecimal.ROUND_HALF_DOWN);
        amount = amount.multiply(rate);
        return amount;
    }

    /**
     * 其他币种转人民币
     * @param sCurNo    持有的货币
     * @param tCurNo    兑换的货币
     * @param amount    兑换的金额
     * @return
     */
    private BigDecimal allToCny(String sCurNo,String tCurNo,BigDecimal amount){
        InfoExchangeDetail detail = BaseServiceBizPool.getInstance().infoExchangeDetailBiz.getExchangeDetailByCurNo(sCurNo);

        if(detail == null){
            throw new BaseCommonBizException("币种" + sCurNo + "未发布或不存在汇率");
        }
        boolean bankFlag = false;
        BigDecimal rate = detail.getmBuyPri();
        if (rate == null || rate.compareTo(BigDecimal.ZERO) != 1){
            rate = detail.getBankConversionPri();
            bankFlag = true;
        }
        rate = rate.divide(detail.getTradeUnit());
        amount = amount.multiply(rate);
        if (!StringUtils.equals(tCurNo, ExchangeConstant.CUR_CNY)){
            amount = cnyToAll(tCurNo,amount,bankFlag);
        }
        return amount;
    }

    public void upload(byte[] bytes, String url){
        InputStream inputStream = new ByteArrayInputStream(bytes);
        Map<InfoExchange, List<InfoExchangeDetail>> map = InfoExchangeImportExcelUtil.importStreamExcel(inputStream, WDWUtil.isExcel2003(url));

        for (Map.Entry<InfoExchange,List<InfoExchangeDetail>> entry : map.entrySet()){

            InfoExchange exchange = entry.getKey();

            InfoExchange exist = infoExchangeMapper.getExchangeByBatchNo(exchange.getBatchNo());
            if(exist != null){
                throw new BaseCommonBizException("批次号已经存在,导入失败");
            }

            // 需要获取汇率的币种
            List<InfoCurrency> curList = BaseServiceBizPool.getInstance().infoCurrencyBiz.getCurrencyListByGainType("1");
            Map<Object,InfoCurrency> groupMap;
            try {
                groupMap = GroupHelper.groupByFieldName(InfoCurrency.class,"currencyName",curList);
            } catch (Exception e) {
                LOG.e(this,"数据分组异常" + e.getMessage(),e);
                throw  new BaseCommonBizException("数据分组异常");
            }

            exchange.setGainWay(InfoRateGainWay.IINSERT_BY_FILE.getCode());
            exchange.setStatus(InfoRateStatus.INITIAL_RELEASE.getCode());
            exchange.setSource(InfoExchangeSource.ZG_BACK.getKey());

            List<InfoExchangeDetail> detailList = entry.getValue();
            for (InfoExchangeDetail detail : detailList){
                InfoCurrency currency = groupMap.get(detail.getCurrencyName().trim());
                if (currency != null){
                    // 设置币种编号
                    detail.setCurrencyNo(currency.getCurrencyNo());
                }
            }

            this.addExchange(exchange, detailList);
        }

    }
    /**
     * 导入外汇汇率明细
     * @param fileUrl
     */
    public void upload(String fileUrl){

        LOG.dStart(this, "读取文件内容开始");
        InputStream inputStream = FastDFSHelper.downloadFile(fileUrl);
        // 解析excel
        Map<InfoExchange, List<InfoExchangeDetail>> map = InfoExchangeImportExcelUtil.importStreamExcel(inputStream, WDWUtil.isExcel2003(fileUrl));

        for (Map.Entry<InfoExchange,List<InfoExchangeDetail>> entry : map.entrySet()){

            InfoExchange exchange = entry.getKey();
            // 验证批次号
            InfoExchange exist = infoExchangeMapper.getExchangeByBatchNo(exchange.getBatchNo());
            if(exist != null){
                throw new BaseCommonBizException("批次号已经存在,导入失败");
            }

            // 需要获取汇率的币种
            List<InfoCurrency> curList = BaseServiceBizPool.getInstance().infoCurrencyBiz.getCurrencyListByGainType("1");
            if (curList == null || curList.size() == 0){
                throw  new BaseCommonBizException("需要设置汇率的币种为空,导入不成功");
            }
            Map<Object,InfoCurrency> groupMap;
            try {
                groupMap = GroupHelper.groupByFieldName(InfoCurrency.class,"currencyName",curList);
            } catch (Exception e) {
                LOG.e(this,"数据分组异常" + e.getMessage(),e);
                throw  new BaseCommonBizException("数据分组异常");
            }

            exchange.setGainWay(InfoRateGainWay.IINSERT_BY_FILE.getCode());
            exchange.setStatus(InfoRateStatus.INITIAL_RELEASE.getCode());
            exchange.setSource(InfoExchangeSource.ZG_BACK.getKey());

            List<InfoExchangeDetail> detailList = entry.getValue();
            for (InfoExchangeDetail detail : detailList){
                InfoCurrency currency = groupMap.get(detail.getCurrencyName());
                if (currency != null){
                    // 设置币种编号
                    detail.setCurrencyNo(currency.getCurrencyNo());
                }
            }

            this.addExchange(exchange, detailList);
        }

        LOG.dStart(this, "读取文件内容结束");
    }

    /**
     * 获取已发布的汇率
     * @return
     */
    public InfoExchange getExchangeByPush(){
        return infoExchangeMapper.getExchangeByStatus(InfoRateStatus.FINISH_RELEASE.getCode());
    }

    /**
     * 获取过期的数据
     * @return
     */
    public List<InfoExchange> getExchangeByOverdue(){
        return infoExchangeMapper.getExchangeByOverdue();
    }


    /**
     * 清除指定日期之前的外汇数据
     * @param clearDate
     */
    public void clearExchange(String clearDate){
        if(StringUtils.isBlank(clearDate)){
            throw new BaseCommonBizException("清理截至日期不能为空");
        }
        List<InfoExchange> list = infoExchangeMapper.getClearExchange(clearDate);
        if (list != null && list.size() > 0){
            for (InfoExchange exchange : list){
                this.deleteExchange(exchange.getUuid());
            }
        }

    }
}
