package com.otc.api.console.ctrl.advertisement.response;

/**
 * Created by lx on 17-4-24.
 */
public class VirtualCoinResp {
    // 名称
    private String name;
    // 简称
    private String shortName;
    // id
    private Long coinId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
