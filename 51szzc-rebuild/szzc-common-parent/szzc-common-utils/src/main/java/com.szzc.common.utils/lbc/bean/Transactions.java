package com.szzc.common.utils.lbc.bean;

import java.math.BigDecimal;

/**
 * Created by lx on 17-6-19.
 */
public class Transactions {

    /**
     * confirmations : 9057
     * date : 2017-06-02 15:43
     * label :
     * timestamp : 1496389394
     * txid : 95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd
     * value : 14.99
     */

    private Long confirmations;
    private String date;
    private String label;
    private Long timestamp;
    private String txid;
    private BigDecimal value;

    public Long getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Long confirmations) {
        this.confirmations = confirmations;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
