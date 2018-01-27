package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 列表
 * Created by zygong on 17-4-27.
 */
public class AdvertisementListReq extends WebApiBaseReq {
    /**
     * 会员信息
     */
    private String filter;
    /**
     * 货币类型
     */
    private Integer symbol;
    /**
     * 开始时间
     */
    private String bigenTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 1、在线购买 2、在线出售
     */
    private Integer transactionType;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getSymbol() {
        return symbol;
    }

    public void setSymbol(Integer symbol) {
        this.symbol = symbol;
    }

    public String getBigenTime() {
        return bigenTime;
    }

    public void setBigenTime(String bigenTime) {
        this.bigenTime = bigenTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }
}
