package com.ruizton.main.controller.admin.robot.request;

import java.math.BigDecimal;

/**
 * Created by lx on 17-5-4.
 */
public class EntrustWarnReq {

    private Integer id;

    private BigDecimal mergeAmount;

    private BigDecimal singleAmount;

    private String type;

    private Integer waitMinute;

    private String mobileNumber;

    private Integer pauseMsgTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMergeAmount() {
        return mergeAmount;
    }

    public void setMergeAmount(BigDecimal mergeAmount) {
        this.mergeAmount = mergeAmount;
    }

    public BigDecimal getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(BigDecimal singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWaitMinute() {
        return waitMinute;
    }

    public void setWaitMinute(Integer waitMinute) {
        this.waitMinute = waitMinute;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getPauseMsgTime() {
        return pauseMsgTime;
    }

    public void setPauseMsgTime(Integer pauseMsgTime) {
        this.pauseMsgTime = pauseMsgTime;
    }
}
