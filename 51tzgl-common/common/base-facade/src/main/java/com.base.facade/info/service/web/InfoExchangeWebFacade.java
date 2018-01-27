package com.base.facade.info.service.web;

import com.base.facade.info.pojo.po.InfoCurrency;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.service.InfoExchangeFacade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
public interface InfoExchangeWebFacade extends InfoExchangeFacade {
    /**
     * 获取可以计算的币种
     * @return
     */
    List<InfoCurrency> getCurrencyListByCalc();

    /**
     * 获取已发布汇率
     * @return
     */
    InfoExchange getExchangeByPush();

    /**
     * 获取汇率明细
     * @param exchangeId 汇率ID
     * @return
     */
    List<InfoExchangeDetail> getExchangeDetailListByExchangeId(String exchangeId);

    /**
     * 货币兑换计算方法
     * @param sCurNo    持有货币
     * @param tCurNo    兑换货币
     * @param amount    持有金额
     * @return
     */
    BigDecimal calcExchangeRate(String sCurNo, String tCurNo, BigDecimal amount);
}
