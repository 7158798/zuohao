package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsParamBean;

/**
 * @author luwei
 * @Date 16-9-30 上午10:30
 */
public class AliDayuParamBean extends SmsParamBean {

    private String appKey;

    private String secret;

    private String freeSignName;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFreeSignName() {
        return freeSignName;
    }

    public void setFreeSignName(String freeSignName) {
        this.freeSignName = freeSignName;
    }
}
