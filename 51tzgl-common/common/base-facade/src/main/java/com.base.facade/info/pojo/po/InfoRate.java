package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class InfoRate extends BasePo {


    /**
     * 银行uuid，对应表字段为：tbl_info_rate.bank_id
     */
    private String bankId;

    /**
     * 批次号，对应表字段为：tbl_info_rate.batch_no
     */
    private String batchNo;

    /**
     * 0：存款利率  1：贷款利率，对应表字段为：tbl_info_rate.type
     */
    private String type;

    /**
     * 0：初始 1 :待发布 2：已发布 3：已失效  4：失败，对应表字段为：tbl_info_rate.status
     */
    private String status;

    /**
     * 获取方式    0:文件导入 1：页面新增 ，对应表字段为：tbl_info_rate.gain_way
     */
    private String gainWay;

    /**
     * 删除标识，true表示用户删除了该记录，对应表字段为：tbl_info_rate.deleted
     */
    private Boolean deleted;

    /**
     * 发布时间，对应表字段为：tbl_info_rate.push_time
     */
    private Date pushTime;

    /**
     * 修改时间，对应表字段为：tbl_info_rate.create_datetime
     */
    private Date createDatetime;

    /**
     * 修改时间，对应表字段为：tbl_info_rate.modifed_datetime
     */
    private Date modifedDatetime;

    private static final long serialVersionUID = 1L;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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