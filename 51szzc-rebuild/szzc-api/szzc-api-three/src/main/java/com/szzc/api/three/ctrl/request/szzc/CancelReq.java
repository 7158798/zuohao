package com.szzc.api.three.ctrl.request.szzc;

/**
 * Created by lx on 17-7-22.
 */
public class CancelReq {

    private Integer userId;
    private Long orderId;
    private Integer symbol;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getSymbol() {
        return symbol;
    }

    public void setSymbol(Integer symbol) {
        this.symbol = symbol;
    }
}
