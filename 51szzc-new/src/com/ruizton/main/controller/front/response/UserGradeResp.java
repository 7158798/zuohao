package com.ruizton.main.controller.front.response;

import java.io.Serializable;

/**
 * Created by a123 on 17-3-7.
 */
public class UserGradeResp implements Serializable{
    private String level;
    private String integral;
    private String outFee;
    private String inCNY;
    private String inBtc;
    private String trade;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getOutFee() {
        return outFee;
    }

    public void setOutFee(String outFee) {
        this.outFee = outFee;
    }

    public String getInCNY() {
        return inCNY;
    }

    public void setInCNY(String inCNY) {
        this.inCNY = inCNY;
    }

    public String getInBtc() {
        return inBtc;
    }

    public void setInBtc(String inBtc) {
        this.inBtc = inBtc;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}
