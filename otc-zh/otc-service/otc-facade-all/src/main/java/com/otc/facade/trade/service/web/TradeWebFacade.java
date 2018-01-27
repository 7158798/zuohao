package com.otc.facade.trade.service.web;

import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.trade.service.TradeFacade;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
public interface TradeWebFacade extends TradeFacade{

    /**
     *  发起交易
     * @param token
     * @param advertId
     * @param account
     */
    Long createTrade(String token, Long advertId, BigDecimal account,String remark);


    /**
     * 买家完成付款
     * @param token
     * @param tradeId
     */
    void buyerPayed(String token, Long tradeId);


    /**
     * 卖家确认  --- 完成交易
     * @param token
     * @param tradeId
     */
    void sellerConfirm(String token, Long tradeId,String vrCode);

    /**
     * 卖家申诉
     * @param token
     * @param tradeId
     */
    void sellerAppeal(String token, Long tradeId);

    /**
     * 买家取消交易
     * @param token
     * @param tradeId
     */
    void buyerCancel(String token, Long tradeId);

    /**
     * 获取交易记录
     * @param vo
     * @return
     */
    TradeVo queryByConditionPage(TradeVo vo);


    /**
     * 获取正在进行中的交易列表
     */
    List<Trade> queryRunningTrade(Long userId);


    /**
     * 系统单条取消交易
     * @param token
     * @param tradeId
     */
    void sysCancel(String token, Long tradeId);
}
