package com.otc.api.web.ctrl.virtual.response;

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

    private String iconUrl;

    private String iconUrl2;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

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

    public String getIconUrl2() {
        return iconUrl2;
    }

    public void setIconUrl2(String iconUrl2) {
        this.iconUrl2 = iconUrl2;
    }
}
