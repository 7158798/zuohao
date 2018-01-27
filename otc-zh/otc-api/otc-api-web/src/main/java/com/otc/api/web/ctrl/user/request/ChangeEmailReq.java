package com.otc.api.web.ctrl.user.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by zh on 16-10-29.
 */
public class ChangeEmailReq extends WebApiBaseReq{

    /**新手机号**/
    private String email;

    /** 验证码验证结果缓存 */
    private String authCodeCache;

    /** 操作步骤 */
    private String stepType;

    private String authCode;

    public String getAuthCodeCache() {
        return authCodeCache;
    }

    public void setAuthCodeCache(String authCodeCache) {
        this.authCodeCache = authCodeCache;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
