package com.otc.api.web.ctrl.user.request;

import java.io.Serializable;

/**
 * Created by yangyy on 15-11-23.
 */
public class EmailVeriCodeReq implements Serializable {

    private String emailAddress;

    private String veryCode;

    private String authCodeCache;


    public String getAuthCodeCache() {
        return authCodeCache;
    }

    public void setAuthCodeCache(String authCodeCache) {
        this.authCodeCache = authCodeCache;
    }

    public String getVeryCode() {
        return veryCode;
    }

    public void setVeryCode(String veryCode) {
        this.veryCode = veryCode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
