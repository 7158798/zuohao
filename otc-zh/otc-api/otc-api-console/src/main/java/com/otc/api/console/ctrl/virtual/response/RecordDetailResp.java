package com.otc.api.console.ctrl.virtual.response;

/**
 * Created by lx on 17-6-6.
 */
public class RecordDetailResp {

    private Long id;
    private String coinName;
    private String amount;
    private String fees;
    private String address;
    private String userName;
    private Boolean isConfirmPwd;
    private Boolean isWalletPwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsConfirmPwd() {
        return isConfirmPwd;
    }

    public void setIsConfirmPwd(Boolean isConfirmPwd) {
        this.isConfirmPwd = isConfirmPwd;
    }

    public Boolean getIsWalletPwd() {
        return isWalletPwd;
    }

    public void setIsWalletPwd(Boolean isWalletPwd) {
        this.isWalletPwd = isWalletPwd;
    }
}
