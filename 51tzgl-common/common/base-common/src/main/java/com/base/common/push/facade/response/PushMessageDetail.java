package com.base.common.push.facade.response;

/**
 * Created by lx on 16-12-6.
 */
public class PushMessageDetail {

    private Integer targetPlatform;

    private Boolean result;

    private String body;

    public Integer getTargetPlatform() {
        return targetPlatform;
    }

    public void setTargetPlatform(Integer targetPlatform) {
        this.targetPlatform = targetPlatform;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
