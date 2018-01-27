package com.szzc.api.three.ctrl.request;

import com.szzc.api.three.annotation.Required;
import com.szzc.api.three.pojo.BaseApiReq;

/**
 * Created by lx on 17-7-23.
 */
public class CancelOrderReq extends BaseApiReq {

    @Required
    private String symbol;
    @Required
    private Long orderId;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
