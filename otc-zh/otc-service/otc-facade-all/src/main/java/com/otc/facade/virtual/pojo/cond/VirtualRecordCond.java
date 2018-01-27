package com.otc.facade.virtual.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-4-20.
 */
public class VirtualRecordCond implements Serializable {
    // 交易id
    private String txId;
    // 记录类型
    private String type;

    private String status;
    // 货币id
    private Long coinId;

    private Long userId;

    private String address;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
