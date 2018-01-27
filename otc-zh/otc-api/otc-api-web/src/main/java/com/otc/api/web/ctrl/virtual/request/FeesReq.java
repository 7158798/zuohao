package com.otc.api.web.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.math.BigDecimal;

/**
 * Created by lx on 17-6-15.
 */
public class FeesReq extends WebApiBaseReq {

    // 提币数量
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
