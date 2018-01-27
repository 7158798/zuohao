package com.base.service.info.impl;

import com.base.facade.info.pojo.po.InfoCurrency;
import com.base.facade.info.pojo.po.InfoExchange;
import com.base.facade.info.pojo.po.InfoExchangeDetail;
import com.base.facade.info.service.InfoExchangeFacade;
import com.base.service.pool.BaseServiceBizPool;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by liuxun on 16-8-22.
 */
public class InfoExchangeFacadeImpl implements InfoExchangeFacade {

    @Override
    public List<InfoCurrency> getCurrencyListByCalc() {
        return BaseServiceBizPool.getInstance().infoCurrencyBiz.getCurrencyListByCalc();
    }

    @Override
    public InfoExchange getExchangeByPush() {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.getExchangeByPush();
    }

    @Override
    public List<InfoExchangeDetail> getExchangeDetailListByExchangeId(String exchangeId) {
        return BaseServiceBizPool.getInstance().infoExchangeDetailBiz.getExchangeDetailListByExchangeId(exchangeId);
    }

    @Override
    public BigDecimal calcExchangeRate(String sCurNo, String tCurNo, BigDecimal amount) {
        return BaseServiceBizPool.getInstance().infoExchangeBiz.calcExchangeRate(sCurNo, tCurNo, amount);
    }
}
