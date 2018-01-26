package com.ruizton.main.controller.app.request;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by fenggq on 17-3-23.
 */
public class BindBankReq extends AppBaseReq{
    private String account;  //帐号
    private int openBankType; //银行type
    private String address;  //支行名称
    private String city;    //开户省市
    private String phoneCode; //验证码
    /**
     * 银行绑定手机号
     */
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getOpenBankType() {
        return openBankType;
    }

    public void setOpenBankType(int openBankType) {
        this.openBankType = openBankType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

}
