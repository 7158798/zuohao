package com.szzc.job.bean;

import java.io.Serializable;

/**
 * Created by zygong on 17-5-25.
 */
public class TickerDateDto implements Serializable {
    private TickerDto ticker;
    private Long date;

    public TickerDto getTicker() {
        return ticker;
    }

    public void setTicker(TickerDto ticker) {
        this.ticker = ticker;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
