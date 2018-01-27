package com.otc.api.console.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-5-2.
 */
public class WalletReq extends WebApiBaseReq {

    private String condition;

    private Long coinId;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
