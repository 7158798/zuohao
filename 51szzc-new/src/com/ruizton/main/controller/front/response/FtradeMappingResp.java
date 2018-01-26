package com.ruizton.main.controller.front.response;

/**
 * Created by a123 on 17-3-13.
 */
public class FtradeMappingResp {
    private String vName;

    private String lowPrice;

    private String hiPrice;

    private String url;


    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHiPrice() {
        return hiPrice;
    }

    public void setHiPrice(String hiPrice) {
        this.hiPrice = hiPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
