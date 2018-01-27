package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class InfoExchange extends BasePo {

    /**
     * 批次号，对应表字段为：tbl_info_exchange.batch_no
     */
    private String batchNo;

    /**
     * 数据来源，对应表字段为：tbl_info_exchange.source
     */
    private String source;

    /**
     * 0：初始 1 :待发布 2：已发布 3：已失效  4：失败，对应表字段为：tbl_info_exchange.status
     */
    private String status;

    /**
     * 获取方式    0:文件导入 1：接口 2：爬虫 ，对应表字段为：tbl_info_exchange.gain_way
     */
    private String gainWay;

    /**
     * 删除标识，true表示用户删除了该记录，对应表字段为：tbl_info_exchange.deleted
     */
    private Boolean deleted;

    /**
     * 发布时间，对应表字段为：tbl_info_exchange.push_time
     */
    private Date pushTime;

    /**
     * 修改时间，对应表字段为：tbl_info_exchange.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_exchange.modifed_datetime
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getGainWay() {
        return gainWay;
    }

    public void setGainWay(String gainWay) {
        this.gainWay = gainWay == null ? null : gainWay.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
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