package com.szzc.facade.wallet.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-5-25.
 */
public class VirtualCapitaloperationCond implements Serializable {

    private Long coinId;
    private Integer type;
    private String txId;
    private String address;
    // 不包含的状态
    private Integer noStatus;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNoStatus() {
        return noStatus;
    }

    public void setNoStatus(Integer noStatus) {
        this.noStatus = noStatus;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
