package com.otc.service.trade.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.trade.service.web.TradeWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.trade.impl.TradeFacadeImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
@Service
public class TradeWebFacadeImpl extends TradeFacadeImpl implements TradeWebFacade{
    @Override
    public Long createTrade(String token, Long advertId, BigDecimal account,String remark) {
        return OTCBizPool.getInstance().tradeBiz.addTade(token, advertId, account,remark);
    }

    @Override
    public void buyerPayed(String token, Long tradeId) {
        OTCBizPool.getInstance().tradeBiz.buyerPay(token, tradeId);
    }

    @Override
    public void sellerConfirm(String token, Long tradeId,String vrCode) {
        OTCBizPool.getInstance().tradeBiz.sellerConfirm(token, tradeId,vrCode);
    }

    @Override
    public void sellerAppeal(String token, Long tradeId) {
        OTCBizPool.getInstance().tradeBiz.sellerAppeal(token, tradeId);
    }

    @Override
    public void buyerCancel(String token, Long tradeId) {
        OTCBizPool.getInstance().tradeBiz.buyerCancel(token, tradeId);
    }

    @Override
    public TradeVo queryByConditionPage(TradeVo vo) {
        List<Trade>  list = OTCBizPool.getInstance().tradeBiz.getByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    @Override
    public List<Trade> queryRunningTrade(Long userId) {
        return OTCBizPool.getInstance().tradeBiz.selectRunTrade(userId);
    }

    @Override
    public void sysCancel(String token, Long tradeId) {
        OTCBizPool.getInstance().tradeBiz.sysCancel(token, tradeId);
    }
}
