package com.base.facade.info.pojo.bean;

import java.util.Date;

/**
 * Created by zh on 16-8-23.
 */
public class InfoRateBean {
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

    private Date createDate;

    private String uuid;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
