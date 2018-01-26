package com.ruizton.main.controller.app.request;

/**
 * Created by zygong on 17-4-18.
 */
public class SecuritySettingReq extends AppBaseReq {
    private String verificationCode; //验证码
    private String idCard; //身份证号
    private String password; //交易密码
    private String phone; // 手机号
    private String verificationCodeOrigin; //原手机验证码
    private String phoneOrigin; //原手机号
    private String verificationCodeNew; //新手机验证码
    private String phoneNew; //新手机号

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCodeOrigin() {
        return verificationCodeOrigin;
    }

    public void setVerificationCodeOrigin(String verificationCodeOrigin) {
        this.verificationCodeOrigin = verificationCodeOrigin;
    }

    public String getPhoneOrigin() {
        return phoneOrigin;
    }

    public void setPhoneOrigin(String phoneOrigin) {
        this.phoneOrigin = phoneOrigin;
    }

    public String getVerificationCodeNew() {
        return verificationCodeNew;
    }

    public void setVerificationCodeNew(String verificationCodeNew) {
        this.verificationCodeNew = verificationCodeNew;
    }

    public String getPhoneNew() {
        return phoneNew;
    }

    public void setPhoneNew(String phoneNew) {
        this.phoneNew = phoneNew;
    }
}
