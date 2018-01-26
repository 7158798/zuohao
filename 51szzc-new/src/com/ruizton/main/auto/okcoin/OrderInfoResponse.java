package com.ruizton.main.auto.okcoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lx on 17-2-13.
 */
public class OrderInfoResponse {

    private Boolean result;
    // 订单集合
    private List<OrderInfo> orders = new ArrayList<OrderInfo>();

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }
}
