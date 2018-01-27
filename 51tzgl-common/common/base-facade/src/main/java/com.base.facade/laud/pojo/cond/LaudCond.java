package com.base.facade.laud.pojo.cond;

import java.io.Serializable;

/**
 * Created by liuxun on 16-10-28.
 */
public class LaudCond implements Serializable {

    private Long laudUserId;

    private String ipAddress;

    private String macAddress;
    //
    private String type;

    private Long relationId;

    public Long getLaudUserId() {
        return laudUserId;
    }

    public void setLaudUserId(Long laudUserId) {
        this.laudUserId = laudUserId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
