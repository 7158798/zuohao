package com.ruizton.main.controller.app.request;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fenggq on 17-3-29.
 */
public class ValidateKycReq extends AppBaseReq {

    private String identityUrlOn;
    private String identityUrlOff;
    private String identityHoldUrl;
    private String validateVideoUrl;

    public String getIdentityUrlOn() {
        return identityUrlOn;
    }

    public void setIdentityUrlOn(String identityUrlOn) {
        this.identityUrlOn = identityUrlOn;
    }

    public String getIdentityUrlOff() {
        return identityUrlOff;
    }

    public void setIdentityUrlOff(String identityUrlOff) {
        this.identityUrlOff = identityUrlOff;
    }

    public String getIdentityHoldUrl() {
        return identityHoldUrl;
    }

    public void setIdentityHoldUrl(String identityHoldUrl) {
        this.identityHoldUrl = identityHoldUrl;
    }

    public String getValidateVideoUrl() {
        return validateVideoUrl;
    }

    public void setValidateVideoUrl(String validateVideoUrl) {
        this.validateVideoUrl = validateVideoUrl;
    }
}
