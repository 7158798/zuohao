package com.otc.api.console.ctrl.trade.resp;

/**
 * Created by fenggq on 17-5-24.
 */
public class TradeJudgeResp {
    private String tradeNo;

    private String sellContent;

    private String sellLevel;

    private String buyLevel;

    private String buyContent;

    public TradeJudgeResp(){
        this.buyLevel = "尚未未评价";
        this.sellLevel = "尚未未评价";
        this.buyContent= "";
        this.sellContent = "";

    }

    public String getSellLevel() {
        return sellLevel;
    }

    public void setSellLevel(String sellLevel) {
        this.sellLevel = sellLevel;
    }

    public String getBuyLevel() {
        return buyLevel;
    }

    public void setBuyLevel(String buyLevel) {
        this.buyLevel = buyLevel;
    }


    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getSellContent() {
        return sellContent;
    }

    public void setSellContent(String sellContent) {
        this.sellContent = sellContent;
    }

    public String getBuyContent() {
        return buyContent;
    }

    public void setBuyContent(String buyContent) {
        this.buyContent = buyContent;
    }
}
