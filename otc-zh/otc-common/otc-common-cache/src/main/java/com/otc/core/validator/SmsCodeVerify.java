package com.otc.core.validator;

/**
 * Created by zhaiyz on 15-11-20.
 */
public class SmsCodeVerify {

    /**
     * true表示认证成功，false表示认证失败
     */
    private boolean status;

    /**
     * status为false时，为失败原因
     */
    private String conetent;

    public SmsCodeVerify(boolean status, String conetent) {
        this.status = status;
        this.conetent = conetent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getConetent() {
        return conetent;
    }

    public void setConetent(String conetent) {
        this.conetent = conetent;
    }

}
