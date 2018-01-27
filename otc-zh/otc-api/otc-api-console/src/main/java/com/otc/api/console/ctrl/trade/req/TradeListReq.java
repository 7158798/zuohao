package com.otc.api.console.ctrl.trade.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-5-15.
 */
public class TradeListReq extends WebApiBaseReq {
    private String tradeNo;

    private String userInfo;

    private Integer status;

    private int type; //1、进行中的交易  2、申诉  3、已完成  4、已取消

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
