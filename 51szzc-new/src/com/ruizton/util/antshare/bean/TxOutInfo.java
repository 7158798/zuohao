package com.ruizton.util.antshare.bean;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-5.
 */
public class TxOutInfo {

    private Integer n;
    // 资产id
    private String asset;
    // 数量
    private BigDecimal value;

    private Integer high;

    private Long low;
    // 地址
    private String address;

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Long getLow() {
        return low;
    }

    public void setLow(Long low) {
        this.low = low;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
