package com.otc.api.web.ctrl.advertisement.request;

import com.jucaifu.common.pojo.vo.BasePageVo;
import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by zygong on 17-5-4.
 */
public class AdvertisementIndexReq extends WebApiBaseReq {
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
}
