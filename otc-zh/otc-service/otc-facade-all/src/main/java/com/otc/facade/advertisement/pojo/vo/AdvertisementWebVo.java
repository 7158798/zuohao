package com.otc.facade.advertisement.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by zygong on 17-5-4.
 */
public class AdvertisementWebVo extends BasePageVo {
    /**
     * 付款方式
     */
    private String payType;

    /**
     * 币种
     */
    private Integer coinType;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序
     */
    private String orderDirection;

    /**
     * 交易类型 （1、在线出售  2、在线购买）
     */
    private Integer transactionType;

    /**
     * 单价
     */
    private Double price;

    /**
     * 1：全部日期   2：仅工作日  3：仅周末节假日
     */
    private Integer advertisementTimeStatus;

    /**
     * 时间
     */
    private String time;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getCoinType() {
        return coinType;
    }

    public void setCoinType(Integer coinType) {
        this.coinType = coinType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAdvertisementTimeStatus() {
        return advertisementTimeStatus;
    }

    public void setAdvertisementTimeStatus(Integer advertisementTimeStatus) {
        this.advertisementTimeStatus = advertisementTimeStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
