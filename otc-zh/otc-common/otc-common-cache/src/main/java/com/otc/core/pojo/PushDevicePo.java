package com.otc.core.pojo;

import java.io.Serializable;

/**
 * PushDevicePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/25.
 */
public class PushDevicePo implements Serializable {

    private String uid;

    private String token;

    private String deviceType;

    private String deviceId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
