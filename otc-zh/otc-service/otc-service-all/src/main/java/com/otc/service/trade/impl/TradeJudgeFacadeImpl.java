package com.otc.service.trade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.service.TradeJudgeFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
@Service
public class TradeJudgeFacadeImpl implements TradeJudgeFacade{
    @Override
    public List<TradeJudge> getByTradeId(Long tradeId) {
        return OTCBizPool.getInstance().tradeJudgeBiz.selectByCondition(tradeId);
    }
}
