package com.szzc.common.utils.lsk.bean;

import java.math.BigDecimal;

/**
 * Created by lx on 17-5-18.
 */
public class Account {

    private String address;

    private BigDecimal unconfirmedBalance;

    private BigDecimal balance;

    private String publicKey;

    private Long unconfirmedSignature;

    private Long secondSignature;

    private String secondPublicKey;

    // multisignatures
    // u_multisignatures


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(BigDecimal unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getUnconfirmedSignature() {
        return unconfirmedSignature;
    }

    public void setUnconfirmedSignature(Long unconfirmedSignature) {
        this.unconfirmedSignature = unconfirmedSignature;
    }

    public Long getSecondSignature() {
        return secondSignature;
    }

    public void setSecondSignature(Long secondSignature) {
        this.secondSignature = secondSignature;
    }

    public String getSecondPublicKey() {
        return secondPublicKey;
    }

    public void setSecondPublicKey(String secondPublicKey) {
        this.secondPublicKey = secondPublicKey;
    }
}
