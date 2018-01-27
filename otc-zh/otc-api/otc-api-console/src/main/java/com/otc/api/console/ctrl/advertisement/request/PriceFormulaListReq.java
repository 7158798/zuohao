package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 币种列表
 * Created by zygong on 17-5-2.
 */
public class PriceFormulaListReq extends WebApiBaseReq {
    /**
     * 币种类型
     */
    private Integer coinId;

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }
}
