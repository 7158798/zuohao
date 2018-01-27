package com.base.facade.info.pojo.poex;

import java.io.Serializable;

/**
 * Created by zh on 16-8-25.
 */
public class InfoRateDetailEx implements Serializable{
    private String item1;

    private String item2;

    private String period;

    private String rate;

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
