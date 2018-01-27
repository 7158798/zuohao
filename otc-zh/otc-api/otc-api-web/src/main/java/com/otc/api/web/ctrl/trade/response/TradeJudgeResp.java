package com.otc.api.web.ctrl.trade.response;

/**
 * Created by fenggq on 17-5-24.
 */
public class TradeJudgeResp {
    private String otherName;

    private String ownerLevel;

    private String ownerContent;

    private String otherLevel;

    private String otherContent;

    public TradeJudgeResp(){
        this.otherLevel = "";
        this.ownerLevel = "";
        this.ownerContent= "";
        this.otherContent = "";
        this.otherName = "";
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOwnerLevel() {
        return ownerLevel;
    }

    public void setOwnerLevel(String ownerLevel) {
        this.ownerLevel = ownerLevel;
    }

    public String getOwnerContent() {
        return ownerContent;
    }

    public void setOwnerContent(String ownerContent) {
        this.ownerContent = ownerContent;
    }

    public String getOtherLevel() {
        return otherLevel;
    }

    public void setOtherLevel(String otherLevel) {
        this.otherLevel = otherLevel;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }
}
