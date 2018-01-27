package com.szzc.common.utils.lbc.bean;

import java.math.BigDecimal;

/**
 * Created by lx on 17-6-22.
 */
public class Outputs {


    /**
     * address : bRtKUbN3j9XPhJNEHzwN797hx3ardrr7vF
     * prevout_n : 0
     * scriptPubKey : 76a914904fb38431c189ca2a8a075451b55b7ba527381188ac
     * type : 1
     * value : 1499000000
     */

    private String address;
    private Integer prevout_n;
    private String scriptPubKey;
    private Integer type;
    private BigDecimal value;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrevout_n() {
        return prevout_n;
    }

    public void setPrevout_n(Integer prevout_n) {
        this.prevout_n = prevout_n;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
