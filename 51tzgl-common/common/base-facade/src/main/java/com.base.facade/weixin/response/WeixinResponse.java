package com.base.facade.weixin.response;

import java.io.Serializable;

/**
 * Created by lx on 16-12-22.
 */
public class WeixinResponse implements Serializable {

    private boolean isSuccess;
    //
    private String errorMsg;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
