package com.szzc.facade.fentrustLog.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.io.Serializable;

public class FentrustVo extends BasePageVo {
    private Long symbol;
    private Long orderId;
    private Integer userId;

    public Long getSymbol() {
        return symbol;
    }

    public void setSymbol(Long symbol) {
        this.symbol = symbol;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}