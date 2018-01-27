package com.otc.api.web.ctrl.user.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 15-11-19.
 */
public class ResetForgetPwdReq extends WebApiBaseReq {

    /** 手机号码 */
    private String emailAddress;
    /** 短信验证码 */
    private String authCode;
    /** 新密码 */
    private String newPassword;
    /** 操作步骤 */
    private String stepType;
    /** 验证码验证结果缓存 */
    private String authCodeCache;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
