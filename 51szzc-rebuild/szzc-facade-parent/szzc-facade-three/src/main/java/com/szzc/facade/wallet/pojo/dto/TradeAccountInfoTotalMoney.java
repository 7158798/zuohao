package com.szzc.facade.wallet.pojo.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zygong on 17-7-19.
 */
public class TradeAccountInfoTotalMoney implements Serializable {

    private List<TradeAccountInfo> list;

    private String totalMoney;

    public List<TradeAccountInfo> getList() {
        return list;
    }

    public void setList(List<TradeAccountInfo> list) {
        this.list = list;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
