package com.szzc.facade.wallet.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-5-25.
 */
public class VirtualWalletCond implements Serializable{


    private Integer coinId;

    private Integer userId;

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
