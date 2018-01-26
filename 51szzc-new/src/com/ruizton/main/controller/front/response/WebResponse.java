package com.ruizton.main.controller.front.response;

/**
 * Created by lx on 17-2-14.
 */
public class WebResponse<T> {

    private int resultCode;

    private String msg;

    private T body;

    public WebResponse(int resultCode,String msg){
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public WebResponse(int resultCode,String msg,T body){
        this.resultCode = resultCode;
        this.msg = msg;
        this.body = body;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
