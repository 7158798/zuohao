package com.szzc.api.three.ctrl.response;

/**
 * Created by zygong on 17-7-21.
 */
public class TradeCancelResp {
    private boolean result;
    private String msg;

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
}
