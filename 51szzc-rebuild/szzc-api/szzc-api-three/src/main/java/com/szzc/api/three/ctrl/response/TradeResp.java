package com.szzc.api.three.ctrl.response;

import com.szzc.api.three.pojo.BaseApiReq;

/**
 * Created by zygong on 17-7-21.
 */
public class TradeResp extends BaseApiReq {
    private int limited;//是否按照市场价买入
    private double tradeAmount;//数量
    private double tradeCnyPrice;//单价
    /**
     * 币种
     */
    private Long symbol;

    public Long getSymbol() {
        return symbol;
    }

    public void setSymbol(Long symbol) {
        this.symbol = symbol;
    }

    public int getLimited() {
        return limited;
    }

    public void setLimited(int limited) {
        this.limited = limited;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public double getTradeCnyPrice() {
        return tradeCnyPrice;
    }

    public void setTradeCnyPrice(double tradeCnyPrice) {
        this.tradeCnyPrice = tradeCnyPrice;
    }
}
