package com.szzc.api.three.ctrl.request;

import com.szzc.api.three.annotation.Required;
import com.szzc.api.three.pojo.BaseApiReq;

/**
 * Created by zygong on 17-7-21.
 */
public class TradeOrderReq extends BaseApiReq {
    @Required
    private String symbol;
    @Required
    private Integer current_page;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }
}
