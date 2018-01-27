package com.base.common.push.facade.messsage;

/**
 * 配置信息
 * Created by liuxun on 16-9-30.
 */
public class PushBean {

    private String ios_api_key;
    private String ios_secret_key;
    private String android_api_key;
    private String android_secret_key;
    // IOS时使用
    private Integer deploy_status;

    public String getIos_api_key() {
        return ios_api_key;
    }

    public void setIos_api_key(String ios_api_key) {
        this.ios_api_key = ios_api_key;
    }

    public String getIos_secret_key() {
        return ios_secret_key;
    }

    public void setIos_secret_key(String ios_secret_key) {
        this.ios_secret_key = ios_secret_key;
    }

    public String getAndroid_api_key() {
        return android_api_key;
    }

    public void setAndroid_api_key(String android_api_key) {
        this.android_api_key = android_api_key;
    }

    public String getAndroid_secret_key() {
        return android_secret_key;
    }

    public void setAndroid_secret_key(String android_secret_key) {
        this.android_secret_key = android_secret_key;
    }

    public Integer getDeploy_status() {
        return deploy_status;
    }

    public void setDeploy_status(Integer deploy_status) {
        this.deploy_status = deploy_status;
    }
}
