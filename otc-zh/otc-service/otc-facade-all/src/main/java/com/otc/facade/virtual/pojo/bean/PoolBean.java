package com.otc.facade.virtual.pojo.bean;

import java.io.Serializable;

/**
 * Created by lx on 17-4-21.
 */
public class PoolBean implements Serializable{
    //　币种id
    private Long coinId;
    //　币种名称
    private String name;
    // 数量
    private String amount;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
