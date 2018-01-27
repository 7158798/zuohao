package com.otc.api.web.ctrl.advertisement.request;

import java.math.BigDecimal;

/**
 * Created by zygong on 17-5-15.
 */
public class TransformReq {
    /**
     * rmb  coin
     */
    private String type;
    private BigDecimal number;
    private Long adId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }
}
