package com.otc.api.web.ctrl.trade.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-5-15.
 */
public class TradeListReq extends WebApiBaseReq {
    private Long coinId ;

    private Integer tradeType;//0 全部  1买  2卖

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
