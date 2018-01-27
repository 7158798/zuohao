package com.szzc.api.three.ctrl.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-7-23.
 */
public class DepthResp implements Serializable {
    // 卖方深度
    private List<List<String>> sells = new ArrayList<>();
    // 买方深度
    private List<List<String>> buys = new ArrayList<>();

    public List<List<String>> getSells() {
        return sells;
    }

    public void setSells(List<List<String>> sells) {
        this.sells = sells;
    }

    public List<List<String>> getBuys() {
        return buys;
    }

    public void setBuys(List<List<String>> buys) {
        this.buys = buys;
    }
}
