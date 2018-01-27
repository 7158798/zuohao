package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class InfoCurrency extends BasePo {

    /**
     * 货币名称，对应表字段为：tbl_info_currency.currency_name
     */
    private String currencyName;

    /**
     * 货币编号，对应表字段为：tbl_info_currency.currency_no
     */
    private String currencyNo;

    /**
     * 获取外汇汇率 0:不获取 1：获取，对应表字段为：tbl_info_currency.gain_type
     */
    private String gainType;

    /**
     * 修改时间，对应表字段为：tbl_info_currency.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_currency.modifed_datetime
     */
    private Date modifedDatetime;

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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName == null ? null : currencyName.trim();
    }

    public String getCurrencyNo() {
        return currencyNo;
    }

    public void setCurrencyNo(String currencyNo) {
        this.currencyNo = currencyNo == null ? null : currencyNo.trim();
    }

    public String getGainType() {
        return gainType;
    }

    public void setGainType(String gainType) {
        this.gainType = gainType == null ? null : gainType.trim();
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
}