package com.szzc.facade.wallet.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-5-25.
 */
public class VirtualAddressCond implements Serializable {

    private Long coinId;

    private String address;

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
