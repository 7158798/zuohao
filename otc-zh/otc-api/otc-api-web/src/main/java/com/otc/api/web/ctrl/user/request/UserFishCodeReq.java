package com.otc.api.web.ctrl.user.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by a123 on 17-4-24.
 */
public class UserFishCodeReq extends WebApiBaseReq {
    private String fishCode;

    public String getFishCode() {
        return fishCode;
    }

    public void setFishCode(String fishCode) {
        this.fishCode = fishCode;
    }
}
