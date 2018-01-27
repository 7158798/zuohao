package com.otc.facade.user.pojo.poex;


import java.io.Serializable;

/**
 * 忘记密码第一步操作验证短信验证码返回结果
 * <p>
 * Created by yangyy on 15-11-19.
 */
public class VerifyResult implements Serializable {
    /**
     * 验证结果
     */
    private boolean result;
    /**
     * 验证码正确结果缓存
     */
    private String authCodeCache;

    /**
     * 执行步骤类型
     */
    private String stepType;

    private String phoneNumber;

    private String respMsg;

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStepType() {
        return  stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getAuthCodeCache() {
        return authCodeCache == null ? "" : authCodeCache;
    }

    public void setAuthCodeCache(String authCodeCache) {
        this.authCodeCache = authCodeCache;
    }
}
