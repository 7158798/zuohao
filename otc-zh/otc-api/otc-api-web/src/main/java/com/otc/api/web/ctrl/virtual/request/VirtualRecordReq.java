package com.otc.api.web.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualRecordReq extends WebApiBaseReq {
    // 类型
    private String type;

    private Long coinId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
