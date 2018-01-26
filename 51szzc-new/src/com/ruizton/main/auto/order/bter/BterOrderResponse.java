package com.ruizton.main.auto.order.bter;

import com.ruizton.main.auto.order.OrderResponse;

import java.util.List;

/**
 * Created by lx on 17-3-1.
 */
public class BterOrderResponse {

    private Boolean result;

    private List<OrderResponse> data;

    private String elapsed;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public List<OrderResponse> getData() {
        return data;
    }

    public void setData(List<OrderResponse> data) {
        this.data = data;
    }
}
