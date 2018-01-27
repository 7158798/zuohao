package com.szzc.api.app.ctrl.request;

/**
 * 价格区间
 * Created by zygong on 17-4-12.
 */
public class PriceRange {
    private Integer id; // 币种id
    private double lowPrice; // 最低价
    private double highPrice; // 最高价

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }
}
