package com.base.spi.response;

import java.io.Serializable;

/**
 * Created by lx on 16-12-14.
 */
public class CallbackResp implements Serializable {

    // 请求是否成功
    private Boolean isSuccess = Boolean.TRUE;
    // 错误信息
    private String errorMsg;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
