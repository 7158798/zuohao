package com.otc.facade.advertisement.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;
import java.util.List;

/**
 * Created by zygong on 17-4-25.
 */
public class AdvertisementVo extends BasePageVo {
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
    private Date bigenTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * userId
     */
    private Long userId;

    /**
     * 1、在线出售 2、在线购买
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

    public Date getBigenTime() {
        return bigenTime;
    }

    public void setBigenTime(Date bigenTime) {
        this.bigenTime = bigenTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }
}
