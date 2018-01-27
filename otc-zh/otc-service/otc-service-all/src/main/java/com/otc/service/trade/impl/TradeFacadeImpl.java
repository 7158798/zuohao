package com.otc.service.trade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.service.TradeFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by a123 on 17-5-11.
 */
@Service
public class TradeFacadeImpl implements TradeFacade{
    @Override
    public Trade selectById(Long tradeId) {
        return OTCBizPool.getInstance().tradeBiz.select(tradeId);
    }
}
