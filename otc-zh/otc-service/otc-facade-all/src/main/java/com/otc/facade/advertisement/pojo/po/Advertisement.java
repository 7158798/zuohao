package com.otc.facade.advertisement.pojo.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jucaifu.common.pojo.po.BasePo;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Advertisement extends BasePo implements Serializable {

    /**
     * 用户，对应表字段为：t_advertisement.user_id
     */
    private Long userId;

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
     * 币种名称
     */
    private String coinName;

    /**
     * 交易价格，对应表字段为：t_advertisement.transaction_price
     */
    private BigDecimal transactionPrice;

    private String transactionPriceStr;

    /**
     * 最小交易额，对应表字段为：t_advertisement.min_trans_amount
     */
    private BigDecimal minTransAmount;

    /**
     * 最大交易额，对应表字段为：t_advertisement.max_trans_amount
     */
    private BigDecimal maxTransAmount;

    /**
     * 付款方式，对应表字段为：t_advertisement.pay_type
     */
    private String payType;

    /**
     * 付款方式备注，对应表字段为：t_advertisement.pay_type_remark
     */
    private String payTypeRemark;

    /**
     * 是否开启安全验证，对应表字段为：t_advertisement.is_open_safety_veri
     */
    private Boolean isOpenSafetyVeri;

    /**
     * 状态（1、已发布 2、已关闭），对应表字段为：t_advertisement.status
     */
    private Integer status;

    /**
     * 创建时间，对应表字段为：t_advertisement.createtime
     */
    private Date createtime;

    /**
     * 价格方式（1、固定价格   2、溢价），对应表字段为：t_advertisement.prict_type
     */
    private Integer priceType;

    /**
     *  交易所，对应表字段为：t_advertisement.trade_platform
     */
    private String tradePlatform;

    /**
     * 前端展示公式
     */
    private String tradePlatformFormula;

    /**
     * 溢价率，对应表字段为：t_advertisement.premium_rate
     */
    private Float premiumRate;

    /**
     * 更新时间，对应表字段为：t_advertisement.updatetime
     */
    private Date updatetime;

    /**
     * 付款方式名称，对应表字段为：t_advertisement.pay_type_name
     */
    private String payTypeName;

    /**
     * 编码，对应表字段为：t_advertisement.code
     */
    private String code;

    /**
     * 计价公式
     */
    private String priceFormula;

    /**
     * 最大成交量，对应表字段为：t_advertisement.max_trans_count
     */
    private BigDecimal maxTransCount;

    /**
     * 最小成交量，对应表字段为：t_advertisement.min_trans_count
     */
    private BigDecimal minTransCount;

    /**
     * 付款期限
     *
     */
    private Long payTime;

    /**
     * 最多成交数量
     */
    private BigDecimal mostDealCount;

    private static final long serialVersionUID = 1L;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Long getCoinType() {
        return coinType;
    }

    public void setCoinType(Long coinType) {
        this.coinType = coinType;
    }

    public BigDecimal getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(BigDecimal transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public BigDecimal getMinTransAmount() {
        return minTransAmount;
    }

    public void setMinTransAmount(BigDecimal minTransAmount) {
        this.minTransAmount = minTransAmount;
    }

    public BigDecimal getMaxTransAmount() {
        return maxTransAmount;
    }

    public void setMaxTransAmount(BigDecimal maxTransAmount) {
        this.maxTransAmount = maxTransAmount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayTypeRemark() {
        return payTypeRemark;
    }

    public void setPayTypeRemark(String payTypeRemark) {
        this.payTypeRemark = payTypeRemark == null ? null : payTypeRemark.trim();
    }

    public Boolean getIsOpenSafetyVeri() {
        return isOpenSafetyVeri;
    }

    public void setIsOpenSafetyVeri(Boolean isOpenSafetyVeri) {
        this.isOpenSafetyVeri = isOpenSafetyVeri;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
        this.tradePlatform = tradePlatform == null ? null : tradePlatform.trim();
    }

    public Float getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Float premiumRate) {
        this.premiumRate = premiumRate;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getPriceFormula() {
        return priceFormula;
    }

    public void setPriceFormula(String priceFormula) {
        this.priceFormula = priceFormula;
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

    public String getTradePlatformFormula() {
        return tradePlatformFormula;
    }

    public void setTradePlatformFormula(String tradePlatformFormula) {
        this.tradePlatformFormula = tradePlatformFormula;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getMostDealCount() {
        return mostDealCount;
    }

    public void setMostDealCount(BigDecimal mostDealCount) {
        this.mostDealCount = mostDealCount;
    }

    public String getTransactionPriceStr() {
        return transactionPriceStr;
    }

    public void setTransactionPriceStr(String transactionPriceStr) {
        this.transactionPriceStr = transactionPriceStr;
    }
}