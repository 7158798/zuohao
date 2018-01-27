package com.otc.facade.trade.service;

import com.otc.facade.trade.pojo.po.Trade;

/**
 * Created by fenggq on 17-5-11.
 */
public interface TradeFacade {

    /**
     *  根据id获取记录
     * @param tradeId
     * @return
     */
    Trade selectById(Long tradeId);
}
