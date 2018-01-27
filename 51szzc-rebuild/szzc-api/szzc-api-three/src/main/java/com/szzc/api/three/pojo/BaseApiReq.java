package com.szzc.api.three.pojo;

import com.szzc.api.three.annotation.Required;

/**
 * Created by zygong on 17-7-21.
 */
public class BaseApiReq {

    @Required
    private String api_key;
    @Required
    private String sign;
    // 时间
    @Required
    private Long nonce;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}
