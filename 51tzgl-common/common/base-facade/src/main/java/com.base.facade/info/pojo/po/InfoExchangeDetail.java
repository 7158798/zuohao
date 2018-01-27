package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.math.BigDecimal;
import java.util.Date;

public class InfoExchangeDetail extends BasePo {

    /**
     * 记录表主键，对应表字段为：tbl_info_exchange_detail.exchange_id
     */
    private String exchangeId;

    /**
     * 货币编号，对应表字段为：tbl_info_exchange_detail.currency_no
     */
    private String currencyNo;

    /**
     * 货币名称，对应表字段为：tbl_info_exchange_detail.currency_name
     */
    private String currencyName;

    /**
     * 交易单位，对应表字段为：tbl_info_exchange_detail.trade_unit
     */
    private BigDecimal tradeUnit;

    /**
     * 现汇买入价，对应表字段为：tbl_info_exchange_detail.f_buy_pri
     */
    private BigDecimal fBuyPri;

    /**
     * 现钞买入价，对应表字段为：tbl_info_exchange_detail.m_buy_pri
     */
    private BigDecimal mBuyPri;

    /**
     * 现汇卖出价，对应表字段为：tbl_info_exchange_detail.f_sell_pri
     */
    private BigDecimal fSellPri;

    /**
     * 先钞卖出价，对应表字段为：tbl_info_exchange_detail.m_sell_pri
     */
    private BigDecimal mSellPri;

    /**
     * 中行折算价，对应表字段为：tbl_info_exchange_detail.bank_conversion_pri
     */
    private BigDecimal bankConversionPri;

    /**
     * 修改时间，对应表字段为：tbl_info_exchange_detail.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_exchange_detail.modifed_datetime
     */
    private Date modifedDatetime;

    /**
     * 数据更新时间，对应表字段为：tbl_info_exchange_detail.update_datetime
     */
    private Date updateDatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId == null ? null : exchangeId.trim();
    }

    public String getCurrencyNo() {
        return currencyNo;
    }

    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo == null ? null : currencyNo.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public BigDecimal getTradeUnit() {
        return tradeUnit;
    }

    public void setTradeUnit(BigDecimal tradeUnit) {
        this.tradeUnit = tradeUnit;
    }

    public BigDecimal getfBuyPri() {
        return fBuyPri;
    }

    public void setfBuyPri(BigDecimal fBuyPri) {
        this.fBuyPri = fBuyPri;
    }

    public BigDecimal getmBuyPri() {
        return mBuyPri;
    }

    public void setmBuyPri(BigDecimal mBuyPri) {
        this.mBuyPri = mBuyPri;
    }

    public BigDecimal getfSellPri() {
        return fSellPri;
    }

    public void setfSellPri(BigDecimal fSellPri) {
        this.fSellPri = fSellPri;
    }

    public BigDecimal getmSellPri() {
        return mSellPri;
    }

    public void setmSellPri(BigDecimal mSellPri) {
        this.mSellPri = mSellPri;
    }

    public BigDecimal getBankConversionPri() {
        return bankConversionPri;
    }

    public void setBankConversionPri(BigDecimal bankConversionPri) {
        this.bankConversionPri = bankConversionPri;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getModifedDatetime() {
        return modifedDatetime;
    }

    public void setModifedDatetime(Date modifedDatetime) {
        this.modifedDatetime = modifedDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}