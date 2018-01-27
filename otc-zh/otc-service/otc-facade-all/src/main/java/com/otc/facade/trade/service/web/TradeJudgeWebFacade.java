package com.otc.facade.trade.service.web;

import com.otc.facade.trade.service.TradeJudgeFacade;

/**
 * Created by fenggq on 17-5-11.
 */
public interface TradeJudgeWebFacade extends TradeJudgeFacade{
    /**
     *  新增评价
     * @param userId
     * @param tradeId
     * @param level
     * @param context
     */
    void addJudage(Long userId,Long tradeId,Integer level,String context);
}
