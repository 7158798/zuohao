package com.otc.api.web.ctrl.user.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by a123 on 17-4-25.
 */
public class UserKycPostReq extends WebApiBaseReq{

    private String identityurlOn;

    private String identityurlOff;

    private String identityurlHold;

    public String getIdentityurlOn() {
        return identityurlOn;
    }

    public void setIdentityurlOn(String identityurlOn) {
        this.identityurlOn = identityurlOn;
    }

    public String getIdentityurlOff() {
        return identityurlOff;
    }

    public void setIdentityurlOff(String identityurlOff) {
        this.identityurlOff = identityurlOff;
    }

    public String getIdentityurlHold() {
        return identityurlHold;
    }

    public void setIdentityurlHold(String identityurlHold) {
        this.identityurlHold = identityurlHold;
    }
}
