package com.ruizton.main.controller.app.request;

import com.ruizton.main.model.Fuser;

import java.sql.Timestamp;

/**
 * 添加银行卡 手机端传输对象
 * Created by zygong on 17-3-30.
 */
public class AddBankReq extends AppBaseReq {
    /**
     * 银行
     */
    private int bankType;
    /**
     * 银行帐号
     */
    private String bankNumber;
    /**
     * 开户行
     */
    private String fothers;
    /**
     * 开户行地址
     */
    private String faddress;
    /**
     * 银行绑定手机号
     */
    private String phone;
    /**
     * 短信验证码
     */
    private String code;

    public int getBankType() {
        return bankType;
    }

    public void setBankType(int bankType) {
        this.bankType = bankType;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getFothers() {
        return fothers;
    }

    public void setFothers(String fothers) {
        this.fothers = fothers;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
