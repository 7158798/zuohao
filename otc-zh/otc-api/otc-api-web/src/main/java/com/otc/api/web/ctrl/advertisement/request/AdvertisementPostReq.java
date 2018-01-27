package com.otc.api.web.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保存广告
 * Created by zygong on 17-4-26.
 */
public class AdvertisementPostReq extends WebApiBaseReq {

    /**
     * 交易类型（1、在线出售  2、在线购买），对应表字段为：t_advertisement.transaction_type
     */
    private Integer transactionType;

    /**
     * 城市，对应表字段为：t_advertisement.city
     */
    private String city;

    /**
     * 币种，对应表字段为：t_advertisement.coin_type
     */
    private Long coinType;

    /**
     * 交易价格，对应表字段为：t_advertisement.transaction_price
     */
    private String transactionPrice;

    /**
     * 最小交易额，对应表字段为：t_advertisement.min_trans_amount
     */
    private BigDecimal minTransCount;

    /**
     * 最大交易额，对应表字段为：t_advertisement.max_trans_amount
     */
    private BigDecimal maxTransCount;

    /**
     * 付款方式，对应表字段为：t_advertisement.pay_type
     */
    private String payType;

    /**
     * 付款方式名称
     */
    private String payTypeName;

    /**
     * 付款方式备注，对应表字段为：t_advertisement.pay_type_remark
     */
    private String payTypeRemark;

    /**
     * 是否开启安全验证，对应表字段为：t_advertisement.is_open_safety_veri
     */
    private Boolean isOpenSafetyVeri;

    /**
     * 价格方式（1、固定价格   2、溢价），对应表字段为：t_advertisement.prict_type
     */
    private Integer priceType;

    /**
     *  交易所，对应表字段为：t_advertisement.trade_platform
     */
    private String tradePlatform;

    /**
     * 溢价率，对应表字段为：t_advertisement.premium_rate
     */
    private Float premiumRate;

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCoinType() {
        return coinType;
    }

    public void setCoinType(Long coinType) {
        this.coinType = coinType;
    }

    public String getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(String transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public BigDecimal getMinTransCount() {
        return minTransCount;
    }

    public void setMinTransCount(BigDecimal minTransCount) {
        this.minTransCount = minTransCount;
    }

    public BigDecimal getMaxTransCount() {
        return maxTransCount;
    }

    public void setMaxTransCount(BigDecimal maxTransCount) {
        this.maxTransCount = maxTransCount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeRemark() {
        return payTypeRemark;
    }

    public void setPayTypeRemark(String payTypeRemark) {
        this.payTypeRemark = payTypeRemark;
    }

    public Boolean getIsOpenSafetyVeri() {
        return isOpenSafetyVeri;
    }

    public void setIsOpenSafetyVeri(Boolean openSafetyVeri) {
        isOpenSafetyVeri = openSafetyVeri;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getTradePlatform() {
        return tradePlatform;
    }

    public void setTradePlatform(String tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    public Float getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Float premiumRate) {
        this.premiumRate = premiumRate;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
