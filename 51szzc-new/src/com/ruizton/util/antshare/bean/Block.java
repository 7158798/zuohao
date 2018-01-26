package com.ruizton.util.antshare.bean;

import java.util.List;

/**
 * Created by lx on 17-4-5.
 */
public class Block {

    private String hash;

    private String size;

    private String version;

    private String previousblockhash;

    private String merkleroot;

    private Long time;

    private Long index;

    private String nonce;

    private String nextminer;
    // 交易信息
    private List<Transaction> tx;
    // 确认数
    private Integer confirmations;

    private String nextblockhash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPreviousblockhash() {
        return previousblockhash;
    }

    public void setPreviousblockhash(String previousblockhash) {
        this.previousblockhash = previousblockhash;
    }

    public String getMerkleroot() {
        return merkleroot;
    }

    public void setMerkleroot(String merkleroot) {
        this.merkleroot = merkleroot;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getNextminer() {
        return nextminer;
    }

    public void setNextminer(String nextminer) {
        this.nextminer = nextminer;
    }

    public List<Transaction> getTx() {
        return tx;
    }

    public void setTx(List<Transaction> tx) {
        this.tx = tx;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public String getNextblockhash() {
        return nextblockhash;
    }

    public void setNextblockhash(String nextblockhash) {
        this.nextblockhash = nextblockhash;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
