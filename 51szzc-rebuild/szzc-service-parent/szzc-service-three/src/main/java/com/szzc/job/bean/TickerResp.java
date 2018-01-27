package com.szzc.job.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 17-6-29.
 */
public class TickerResp implements Serializable {
    // 上次数据的日期
    private String date;
    // 各平台直接的价格
    private List<Ticker> ticker;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Ticker> getTicker() {
        return ticker;
    }

    public void setTicker(List<Ticker> ticker) {
        this.ticker = ticker;
    }
}
