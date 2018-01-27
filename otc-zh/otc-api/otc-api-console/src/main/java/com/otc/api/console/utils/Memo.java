/** 
 * Creation Date:2015-4-30下午1:31:52 
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved. 
 */
package com.otc.api.console.utils;

/**
 * 用户购买的手机信息
 * 
 * @author zhaiyz
 */
public class Memo {

    /**
     * 手机型号
     */
    private String model;

    /**
     * 颜色
     */
    private String color;

    /**
     * 套餐月费
     */
    private String monthFee;

    /**
     * 合约期限
     */
    private String period;

    /**
     * 是否预存话费：Y：预存，N：不预存
     */
    private String prestore;

    /**
     * 参加合约的手要号
     */
    private String phoneNumber;

    /**
     * 新老手机号标识，0：老手机号，1：新手机号
     */
    private int phoneNumberType;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(String monthFee) {
        this.monthFee = monthFee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrestore() {
        return prestore;
    }

    public void setPrestore(String prestore) {
        this.prestore = prestore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(int phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }
}
