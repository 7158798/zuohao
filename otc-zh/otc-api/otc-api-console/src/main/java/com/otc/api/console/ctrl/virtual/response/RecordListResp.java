package com.otc.api.console.ctrl.virtual.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.api.console.base.UserCommonResp;
import com.otc.facade.virtual.enums.VirtualRecordOutStatus;
import com.otc.facade.virtual.enums.VirtualRecordType;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.poex.VirtualRecordEx;

/**
 * Created by lx on 17-5-2.
 */
public class RecordListResp extends UserCommonResp{

    private Long id;

    private String coinName;

    private String type;

    private String typeCode;

    private String confirmations;

    private String amount;

    private String fees;

    private String address;

    private String status;

    private String statusCode;

    private String createDate;

    private String updateDate;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void copy(VirtualRecordEx recordEx){
        super.setRealName(recordEx.getRealName());
        super.setEmailAddress(recordEx.getEmailAddress());
        this.coinName = recordEx.getCoinName();
            super.setUserId(recordEx.getUserId());
            this.id = recordEx.getId();
            this.typeCode = recordEx.getType();
            this.address = recordEx.getAddress();
            this.type = VirtualRecordType.getTypeByCode(typeCode).getDesc();
            this.statusCode = recordEx.getStatus();
            this.status = VirtualRecordOutStatus.getDescByCode(statusCode);
            this.amount = recordEx.getAmount().toString();
            this.fees = recordEx.getFees().toPlainString();
            this.confirmations = recordEx.getConfirmations().toString();
            this.createDate = DateHelper.date2String(recordEx.getCreateDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
            this.updateDate = DateHelper.date2String(recordEx.getModifiedDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);

    }
}
