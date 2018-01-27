package com.otc.api.web.ctrl.user.response;

/**
 * Created by fenggq on 15-11-19.
 */
public class ResetForgetPwdResp {

    private String email;

    private String stepType;

    private String authCodeCache;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getAuthCodeCache() {
        return authCodeCache;
    }

    public void setAuthCodeCache(String authCodeCache) {
        this.authCodeCache = authCodeCache;
    }
}
