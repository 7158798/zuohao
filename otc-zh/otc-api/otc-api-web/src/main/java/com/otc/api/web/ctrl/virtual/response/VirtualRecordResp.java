package com.otc.api.web.ctrl.virtual.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.common.api.utils.DecimalUtils;
import com.otc.facade.virtual.enums.VirtualRecordInStatus;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.po.VirtualRecord;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualRecordResp {

    private Long recordId;

    private String address;

    private String amount;

    private String fee;

    private String status;

    private String statusCode;

    private String remark;

    private String updateDate;

    private String confirmations;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    public void copy(VirtualRecord record){
        setRecordId(record.getId());
        setAddress(record.getAddress());
        setConfirmations(record.getConfirmations().toString());
        setAmount(DecimalUtils.formatAmount(record.getAmount()));
        setFee(record.getFees().toPlainString());
        setStatusCode(record.getStatus());
        setRemark(record.getRemark());
        String status;
        if (record.getType().equals(VirtualRecordType.RECHARGE.getCode())){
            status = VirtualRecordInStatus.getDescByCode(record.getStatus());
        } else {
            status = VirtualRecordOutStatus.getWebDescByCode(record.getStatus());
        }
        setStatus(status);
        setUpdateDate(DateHelper.date2String(record.getModifiedDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
    }
}
