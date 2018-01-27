package com.base.common.sms.facade.response;

/**
 * @author luwei
 * @Date 16-9-29 上午10:10
 */
public class SmsResultResp {

    private boolean result;
    private String msg;
    private String body;

    public SmsResultResp() {
        result = Boolean.TRUE;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
