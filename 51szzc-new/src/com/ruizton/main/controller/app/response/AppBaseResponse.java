package com.ruizton.main.controller.app.response;

import java.io.Serializable;

/**
 * Created by a123 on 17-3-23.
 */
public class AppBaseResponse implements Serializable{
    protected String result;

    protected String code;


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
