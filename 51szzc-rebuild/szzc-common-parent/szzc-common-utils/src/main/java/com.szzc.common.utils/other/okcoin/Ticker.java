package com.szzc.common.utils.other.okcoin;

import java.math.BigDecimal;

/**
 * Created by lx on 17-6-28.
 */
public class Ticker {


    /**
     * buy : 33.15
     * high : 34.15
     * last : 33.15
     * low : 32.05
     * sell : 33.16
     * vol : 10532696.39199642
     */

    private BigDecimal buy;
    private BigDecimal high;
    private BigDecimal last;
    private BigDecimal low;
    private BigDecimal sell;
    private BigDecimal vol;

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }
}
