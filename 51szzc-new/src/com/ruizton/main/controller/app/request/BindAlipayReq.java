package com.ruizton.main.controller.app.request;

/**
 * Created by a123 on 17-3-22.
 */
public class BindAlipayReq extends AppBaseReq{

    private String account;


    private String name;

    private String verificationCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
