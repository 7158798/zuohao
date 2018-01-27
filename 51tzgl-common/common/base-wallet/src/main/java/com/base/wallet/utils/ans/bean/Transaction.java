package com.base.wallet.utils.ans.bean;

import java.util.List;

/**
 * Created by lx on 17-4-5.
 */
public class Transaction {

    private String txid;

    private Integer size;

    private String type;

    private Integer version;

    private List<TxOutInfo> vout;
    // 确认数
    private Integer confirmations;
    // 区块hash
    private String blockhash;

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<TxOutInfo> getVout() {
        return vout;
    }

    public void setVout(List<TxOutInfo> vout) {
        this.vout = vout;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }
}
