package com.otc.facade.virtual.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirtualRecord extends BasePo {

    /**
     * 对应表字段为：t_virtual_record.user_id
     */
    private Long userId;

    /**
     * 对应表字段为：t_virtual_record.type
     */
    private String type;

    /**
     * 对应表字段为：t_virtual_record.status
     */
    private String status;

    /**
     * 对应表字段为：t_virtual_record.remark
     */
    private String remark;

    /**
     * 对应表字段为：t_virtual_record.address
     */
    private String address;

    /**
     * 对应表字段为：t_virtual_record.amount
     */
    private BigDecimal amount;

    /**
     * 对应表字段为：t_virtual_record.confirmations
     */
    private Integer confirmations;

    /**
     * 对应表字段为：t_virtual_record.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_virtual_record.update_date
     */
    private Date modifiedDate;

    /**
     * 对应表字段为：t_virtual_record.fees
     */
    private BigDecimal fees;

    /**
     * 对应表字段为：t_virtual_record.tx_id
     */
    private String txId;

    /**
     * 对应表字段为：t_virtual_record.coin_id
     */
    private Long coinId;

    /**
     * 对应表字段为：t_virtual_record.reason
     */
    private String reason;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId == null ? null : txId.trim();
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}