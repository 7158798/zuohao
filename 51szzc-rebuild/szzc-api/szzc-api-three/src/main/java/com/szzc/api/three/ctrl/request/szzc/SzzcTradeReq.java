package com.szzc.api.three.ctrl.request.szzc;

import java.math.BigDecimal;

/**
 * Created by lx on 17-7-22.
 */
public class SzzcTradeReq {
    private Integer userId;
    private Integer limited;
    private Integer symbol;
    private BigDecimal tradeAmount;
    private BigDecimal tradeCnyPrice;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public Integer getSymbol() {
        return symbol;
    }

    public void setSymbol(Integer symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getTradeCnyPrice() {
        return tradeCnyPrice;
    }

    public void setTradeCnyPrice(BigDecimal tradeCnyPrice) {
        this.tradeCnyPrice = tradeCnyPrice;
    }
}
