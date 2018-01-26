package com.ruizton.main.auto.order;

import com.ruizton.main.model.Fentrust;

/**
 * Created by lx on 17-2-13.
 */
public class OrderResult extends Result {
    // 订单信息
    private Fentrust fentrust;

    public Fentrust getFentrust() {
        return fentrust;
    }

    public void setFentrust(Fentrust fentrust) {
        this.fentrust = fentrust;
    }
}
