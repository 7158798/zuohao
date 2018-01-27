package com.szzc.api.three.ctrl.request;

import com.szzc.api.three.pojo.BaseApiReq;

import java.math.BigDecimal;

/**
 * Created by zygong on 17-7-21.
 */
public class TradeReq extends BaseApiReq {
    /**
     * 币种
     */
    private String symbol;
    /**
     * 买卖类型
     */
    private String type;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private BigDecimal amount;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
