package com.otc.api.web.ctrl.trade.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.math.BigDecimal;

/**
 * Created by fenggq on 17-5-11.
 */
public class TradeReq extends WebApiBaseReq{
    //广告id
    private Long advertId;

    //交易数量
    private BigDecimal tradeNumber;

    //备注信息
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Long advertId) {
        this.advertId = advertId;
    }

    public BigDecimal getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(BigDecimal tradeNumber) {
        this.tradeNumber = tradeNumber;
    }
}
