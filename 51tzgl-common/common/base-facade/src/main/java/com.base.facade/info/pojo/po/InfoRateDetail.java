package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class  InfoRateDetail extends BasePo {

    /**
     * 记录表UUID，对应表字段为：tbl_info_rate_detail.rate_id
     */
    private String rateId;

    /**
     * 项目1，对应表字段为：tbl_info_rate_detail.item_fisrt
     */
    private String itemFisrt;

    /**
     * 项目2，对应表字段为：tbl_info_rate_detail.item_second
     */
    private String itemSecond;

    /**
     * 期限，对应表字段为：tbl_info_rate_detail.period
     */
    private String period;

    /**
     * 年利率，对应表字段为：tbl_info_rate_detail.rate
     */
    private String rate;

    /**
     * 修改时间，对应表字段为：tbl_info_rate_detail.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_rate_detail.modifed_datetime
     */
    private Date modifedDatetime;

    private static final long serialVersionUID = 1L;

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId == null ? null : rateId.trim();
    }

    public String getItemFisrt() {
        return itemFisrt;
    }

    public void setItemFisrt(String itemFisrt) {
        this.itemFisrt = itemFisrt == null ? null : itemFisrt.trim();
    }

    public String getItemSecond() {
        return itemSecond;
    }

    public void setItemSecond(String itemSecond) {
        this.itemSecond = itemSecond == null ? null : itemSecond.trim();
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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