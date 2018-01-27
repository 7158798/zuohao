package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsParamBean;

/**
 * Created by luwei on 17-6-21.
 */
public class AWSParamBean extends SmsParamBean {

    private String accessKey;

    private String secretKey;

    private String smsRegion;

    private String smsSignature;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSmsRegion() {
        return smsRegion;
    }

    public void setSmsRegion(String smsRegion) {
        this.smsRegion = smsRegion;
    }

    public String getSmsSignature() {
        return smsSignature;
    }

    public void setSmsSignature(String smsSignature) {
        this.smsSignature = smsSignature;
    }
}
