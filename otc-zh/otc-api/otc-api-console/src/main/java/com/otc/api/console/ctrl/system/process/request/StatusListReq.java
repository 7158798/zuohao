package com.otc.api.console.ctrl.system.process.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-5-25.
 */
public class StatusListReq extends WebApiBaseReq {
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
