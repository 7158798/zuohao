package com.otc.facade.trade.service;

import com.otc.facade.trade.pojo.po.TradeJudge;

import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
public interface TradeJudgeFacade {

    /**
     * 通过交易id获取评价
     * @param tradeId
     * @return
     */
    List<TradeJudge> getByTradeId(Long tradeId);
}
