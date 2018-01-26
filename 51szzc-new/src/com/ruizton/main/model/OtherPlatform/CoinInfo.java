package com.ruizton.main.model.OtherPlatform;

/**
 * Created by zygong on 17-3-23.
 */
public class CoinInfo {

    private double hight;
    private double low;
    private double buy;
    private double sell;
    private double last;
    private double vol;

    public CoinInfo(double hight, double low, double buy, double sell, double last, double vol) {
        this.hight = hight;
        this.low = low;
        this.buy = buy;
        this.sell = sell;
        this.last = last;
        this.vol = vol;
    }

    public double getHight() {
        return hight;
    }

    public void setHight(double hight) {
        this.hight = hight;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }
}
