package com.base.facade.info.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.math.BigDecimal;
import java.util.Date;

public class InfoBankProduct extends BasePo {

    /**
     * 银行uuid，对应表字段为：tbl_info_bank_product.bank_id
     */
    private String bankId;

    /**
     * 批次号，对应表字段为：tbl_info_bank_product.batch_no
     */
    private String batchNo;

    /**
     * 期限，对应表字段为：tbl_info_bank_product.period
     */
    private Integer period;

    /**
     * 预期年化利率，对应表字段为：tbl_info_bank_product.expected_interest_rate
     */
    private BigDecimal expectedInterestRate;

    /**
     * 起购金额，对应表字段为：tbl_info_bank_product.min_investment_amount
     */
    private BigDecimal minInvestmentAmount;

    /**
     * 0：初始 2：已发布 3：已失效 ，对应表字段为：tbl_info_bank_product.status
     */
    private String status;

    /**
     * 发布时间，对应表字段为：tbl_info_bank_product.push_date
     */
    private Date pushDate;

    /**
     * 创建时间，对应表字段为：tbl_info_bank_product.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：tbl_info_bank_product.modifed_date
     */
    private Date modifedDate;

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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getExpectedInterestRate() {
        return expectedInterestRate;
    }

    public void setExpectedInterestRate(BigDecimal expectedInterestRate) {
        this.expectedInterestRate = expectedInterestRate;
    }

    public BigDecimal getMinInvestmentAmount() {
        return minInvestmentAmount;
    }

    public void setMinInvestmentAmount(BigDecimal minInvestmentAmount) {
        this.minInvestmentAmount = minInvestmentAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifedDate() {
        return modifedDate;
    }

    public void setModifedDate(Date modifedDate) {
        this.modifedDate = modifedDate;
    }
}