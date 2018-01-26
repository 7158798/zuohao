package com.ruizton.main.model.vo;

import com.ruizton.main.model.Fapppush;

/**
 * Created by zygong on 17-4-18.
 */
public class FapppushVo extends Fapppush {
    private String coinName; //币种名称
    private String shortName; // 币种简称
    private String url; // logo

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
