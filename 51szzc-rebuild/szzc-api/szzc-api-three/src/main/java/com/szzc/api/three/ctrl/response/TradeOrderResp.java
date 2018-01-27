package com.szzc.api.three.ctrl.response;

import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;

import java.util.List;

/**
 * Created by zygong on 17-7-21.
 */
public class TradeOrderResp {
    public boolean result;
    private List<FentrustDto> orders;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<FentrustDto> getOrders() {
        return orders;
    }

    public void setOrders(List<FentrustDto> orders) {
        this.orders = orders;
    }
}
