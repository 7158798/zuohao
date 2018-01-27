package com.szzc.api.three.pojo;

import java.io.Serializable;

/**
 * BaseAppReq
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/9.
 */
public class BaseAppReq implements Serializable {

    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
