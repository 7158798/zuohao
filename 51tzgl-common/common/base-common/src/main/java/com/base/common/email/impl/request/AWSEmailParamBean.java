package com.base.common.email.impl.request;

import com.base.common.email.facade.request.EmailParamBean;

/**
 * Created by luwei on 17-6-16.
 */
public class AWSEmailParamBean extends EmailParamBean{

    private String accessKey;

    private String secretKey;

    private String emailRegion;

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

    public String getEmailRegion() {
        return emailRegion;
    }

    public void setEmailRegion(String emailRegion) {
        this.emailRegion = emailRegion;
    }
}
