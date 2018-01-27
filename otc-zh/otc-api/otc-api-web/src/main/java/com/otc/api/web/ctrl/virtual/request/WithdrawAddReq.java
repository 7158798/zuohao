package com.otc.api.web.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-4-25.
 */
public class WithdrawAddReq extends WebApiBaseReq {

    private Long coinId;

    private String address;

    private String amount;

    private String fees;

    private String mailCode;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }
}
