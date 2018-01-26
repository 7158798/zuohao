package com.ruizton.main.controller.app.request;

import java.io.Serializable;

/**
 * Created by zygong on 17-4-18.
 */
public class AppEmailReq extends AppBaseReq{
    private String email;
    private Integer type;
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
