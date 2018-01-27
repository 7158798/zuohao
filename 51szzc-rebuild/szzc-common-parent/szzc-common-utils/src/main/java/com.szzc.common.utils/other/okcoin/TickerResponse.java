package com.szzc.common.utils.other.okcoin;

/**
 * Created by lx on 17-2-13.
 */
public class TickerResponse {

    private String date;

    private Ticker ticker;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }
}
