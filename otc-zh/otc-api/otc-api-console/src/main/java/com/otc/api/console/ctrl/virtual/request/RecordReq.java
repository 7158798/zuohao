package com.otc.api.console.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-5-2.
 */
public class RecordReq extends WebApiBaseReq{

    private String status;
    // 查询条件
    private String condition;

    private Long coinId;

    private String type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
