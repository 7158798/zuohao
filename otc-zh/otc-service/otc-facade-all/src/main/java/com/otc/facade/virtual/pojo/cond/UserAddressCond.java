package com.otc.facade.virtual.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-4-19.
 */
public class UserAddressCond implements Serializable {

    private Long userId;

    private Long coinId;

    private String address;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
