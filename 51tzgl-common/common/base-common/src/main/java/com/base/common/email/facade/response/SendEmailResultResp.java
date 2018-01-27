package com.base.common.email.facade.response;

/**
 * Created by luwei on 17-6-16.
 */
public class SendEmailResultResp {

    private boolean result;
    private String msg;
    private String body;


    public SendEmailResultResp() {
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
