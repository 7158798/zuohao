package com.ruizton.main.model.OtherPlatform;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-3-23.
 */
public class OtherPlatform {
    private BigDecimal update;
    private String type;
    private double usdcny;
    private CoinInfo last;
    private CoinInfo now;

    public OtherPlatform(BigDecimal update, String type, double usdcny, CoinInfo last, CoinInfo now) {
        this.update = update;
        this.type = type;
        this.usdcny = usdcny;
        this.last = last;
        this.now = now;
    }

    public BigDecimal getUpdate() {
        return update;
    }

    public void setUpdate(BigDecimal update) {
        this.update = update;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getUsdcny() {
        return usdcny;
    }

    public void setUsdcny(double usdcny) {
        this.usdcny = usdcny;
    }

    public CoinInfo getLast() {
        return last;
    }

    public void setLast(CoinInfo last) {
        this.last = last;
    }

    public CoinInfo getNow() {
        return now;
    }

    public void setNow(CoinInfo now) {
        this.now = now;
    }
}
