package com.base.wallet.utils.lsk.resp;

/**
 * Created by lx on 17-5-15.
 */
public class BaseJson {
    // 接口请求的状态
    private Boolean success;

    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
