package com.ruizton.main.controller.app.request;

import java.util.List;

/**
 * 价格区间推送 接收对象
 * Created by zygong on 17-4-12.
 */
public class AppPushReq {
    private Integer sendType; // 1：不发送  2：发送
    private String loginToken; // token
    private PriceRange priceRange;
    private String phoneCode; // 手机标识
    private Integer phoneType;  // 手机类型

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public PriceRange getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(PriceRange priceRange) {
        this.priceRange = priceRange;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public Integer getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(Integer phoneType) {
        this.phoneType = phoneType;
    }
}
