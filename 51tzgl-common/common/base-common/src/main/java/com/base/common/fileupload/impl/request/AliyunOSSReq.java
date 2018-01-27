package com.base.common.fileupload.impl.request;

/**
 * @author luwei
 * @Date 12/2/16 2:06 PM
 */
public class AliyunOSSReq {

    //数据访问点
    private String endpoint;

    //主账户key
    private String accessKeyId;

    //主账户secret
    private String accessKeySecret;

    //子账户key
    private String ramAccessKeyId;

    //子账户KeySecret
    private String ramAccessKeySecret;

    //角色arm
    private String ramRoleArm;


    public AliyunOSSReq(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public AliyunOSSReq(String endpoint, String accessKeyId, String accessKeySecret, String ramAccessKeyId, String ramAccessKeySecret, String ramRoleArm) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.ramAccessKeyId = ramAccessKeyId;
        this.ramAccessKeySecret = ramAccessKeySecret;
        this.ramRoleArm = ramRoleArm;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRamAccessKeyId() {
        return ramAccessKeyId;
    }

    public void setRamAccessKeyId(String ramAccessKeyId) {
        this.ramAccessKeyId = ramAccessKeyId;
    }

    public String getRamAccessKeySecret() {
        return ramAccessKeySecret;
    }

    public void setRamAccessKeySecret(String ramAccessKeySecret) {
        this.ramAccessKeySecret = ramAccessKeySecret;
    }

    public String getRamRoleArm() {
        return ramRoleArm;
    }

    public void setRamRoleArm(String ramRoleArm) {
        this.ramRoleArm = ramRoleArm;
    }
}
