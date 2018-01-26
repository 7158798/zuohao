package com.ruizton.main.controller.app.request;

/**
 * Created by zygong on 17-4-18.
 */
public class TransactionSettingReq extends AppBaseReq {
    private Integer bankId; //银行卡id
    private String verificationCode; //验证码
    private Integer AlipayId; //支付宝id
    private Integer symbol; // 币种id
    private String virtualAddress; //虚拟币地址
    private String remark; //备注
    private Integer virtualAddressId; // 虚拟币地址id

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getAlipayId() {
        return AlipayId;
    }

    public void setAlipayId(Integer alipayId) {
        AlipayId = alipayId;
    }

    public Integer getSymbol() {
        return symbol;
    }

    public void setSymbol(Integer symbol) {
        this.symbol = symbol;
    }

    public String getVirtualAddress() {
        return virtualAddress;
    }

    public void setVirtualAddress(String virtualAddress) {
        this.virtualAddress = virtualAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVirtualAddressId() {
        return virtualAddressId;
    }

    public void setVirtualAddressId(Integer virtualAddressId) {
        this.virtualAddressId = virtualAddressId;
    }
}
