package com.otc.service.trade.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.trade.service.TradeJudgeFacade;
import com.otc.facade.trade.service.web.TradeJudgeWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.trade.impl.TradeJudgeFacadeImpl;

/**
 * Created by fenggq on 17-5-11.
 */
@Service
public class TradeJudgeWebFacadeImpl extends TradeJudgeFacadeImpl implements TradeJudgeWebFacade{

    @Override
    public void addJudage(Long userId, Long tradeId, Integer level, String context) {
        OTCBizPool.getInstance().tradeJudgeBiz.addJudage(userId, tradeId, level, context);
    }
}
