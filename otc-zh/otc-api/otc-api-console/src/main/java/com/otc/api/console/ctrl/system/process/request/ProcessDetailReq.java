package com.otc.api.console.ctrl.system.process.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-5-24.
 */
public class ProcessDetailReq extends WebApiBaseReq{
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
