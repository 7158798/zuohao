package com.otc.api.web.ctrl.user.response;

/**
 * Created by fenggq on 17-6-19.
 */
public class NoReadMessageResp {
    private Integer messageNum;

    private Integer tradeNum;

    public Integer getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(Integer messageNum) {
        this.messageNum = messageNum;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }
}
