package com.ruizton.main.auto.okcoin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-2-13.
 */
public class TickerResponse {

    private String date;

    private Map<String,String> ticker = new HashMap<String, String>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getTicker() {
        return ticker;
    }

    public void setTicker(Map<String, String> ticker) {
        this.ticker = ticker;
    }
}
