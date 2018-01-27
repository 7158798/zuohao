package com.otc.facade.advertisement.pojo.dto;

import com.jucaifu.common.pojo.po.BasePo;
import com.jucaifu.common.util.StringHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class AdvertisementDto extends BasePo implements Serializable {

    /**
     * 用户，对应表字段为：t_advertisement.user_id
     */
    private Long userId;

    /**
     * 名称
     */
    private String  userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 好评率
     */
    private String favorableRate;

    /**
     * 成交量
     */
    private Integer dealCount;

    /**
     * 交易价格，对应表字段为：t_advertisement.transaction_price
     */
    private BigDecimal transactionPrice;

    private String transactionPriceStr;

    /**
     * 最小交易额，对应表字段为：t_advertisement.min_trans_amount
     */
    private BigDecimal minTransAmount;

    private String minTransAmountStr;

    /**
     * 最大交易额，对应表字段为：t_advertisement.max_trans_amount
     */
    private BigDecimal maxTransAmount;

    private String maxTransAmountStr;

    /**
     * 最大成交量，对应表字段为：t_advertisement.max_trans_count
     */
    private BigDecimal maxTransCount;

    /**
     * 最小成交量，对应表字段为：t_advertisement.min_trans_count
     */
    private BigDecimal minTransCount;

    /**
     * 付款方式名称，对应表字段为：t_advertisement.pay_type_name
     */
    private String payTypeName;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFavorableRate() {
        if(StringUtils.isNotBlank(favorableRate)){
            return StringHelper.percent(favorableRate, 0);
        }else {
            return "0";
        }
    }

    public void setFavorableRate(String favorableRate) {
        this.favorableRate = favorableRate;
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public String getTransactionPriceStr() {
        return transactionPriceStr;
    }

    public void setTransactionPriceStr(String transactionPriceStr) {
        this.transactionPriceStr = transactionPriceStr;
    }

    public BigDecimal getMinTransAmount() {
        return minTransAmount;
    }

    public void setMinTransAmount(BigDecimal minTransAmount) {
        this.minTransAmount = minTransAmount;
    }

    public String getMinTransAmountStr() {
        return minTransAmountStr;
    }

    public void setMinTransAmountStr(String minTransAmountStr) {
        this.minTransAmountStr = minTransAmountStr;
    }

    public BigDecimal getMaxTransAmount() {
        return maxTransAmount;
    }

    public void setMaxTransAmount(BigDecimal maxTransAmount) {
        this.maxTransAmount = maxTransAmount;
    }

    public String getMaxTransAmountStr() {
        return maxTransAmountStr;
    }

    public void setMaxTransAmountStr(String maxTransAmountStr) {
        this.maxTransAmountStr = maxTransAmountStr;
    }

    public BigDecimal getMaxTransCount() {
        return maxTransCount;
    }

    public void setMaxTransCount(BigDecimal maxTransCount) {
        this.maxTransCount = maxTransCount;
    }

    public BigDecimal getMinTransCount() {
        return minTransCount;
    }

    public void setMinTransCount(BigDecimal minTransCount) {
        this.minTransCount = minTransCount;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}