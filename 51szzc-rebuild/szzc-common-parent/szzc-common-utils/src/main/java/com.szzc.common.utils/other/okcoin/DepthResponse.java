package com.szzc.common.utils.other.okcoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-2-16.
 */
public class DepthResponse {

    // 卖方深度
    private List<BigDecimal[]> asks = new ArrayList<BigDecimal[]>();
    // 买方深度
    private List<BigDecimal[]> bids = new ArrayList<BigDecimal[]>();

    public List<BigDecimal[]> getAsks() {
        return asks;
    }

    public void setAsks(List<BigDecimal[]> asks) {
        this.asks = asks;
    }

    public List<BigDecimal[]> getBids() {
        return bids;
    }

    public void setBids(List<BigDecimal[]> bids) {
        this.bids = bids;
    }
}
