package com.ruizton.main.model;

import com.ruizton.util.Utils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by a123 on 17-3-28.
 */
@Entity
@Table(name = "fvalidatekyc")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvalidatekyc {
    private int id;
    private Timestamp createTime;
    private String identityUrlOn;
    private String identityUrlOff;
    private String identityHoldUrl;
    private String validateVideoUrl;
    private Timestamp auditTime;
    private Integer status;
    private String cancelReason;
    private Fuser fuser;
    private Fadmin fadmin;
    private String bankName;
    private String bankNumber;

    private String bankNumber_s;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "createTime")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "identityUrlOn")
    public String getIdentityUrlOn() {
        return identityUrlOn;
    }

    public void setIdentityUrlOn(String identityUrlOn) {
        this.identityUrlOn = identityUrlOn;
    }

    @Basic
    @Column(name = "identityUrlOff")
    public String getIdentityUrlOff() {
        return identityUrlOff;
    }

    public void setIdentityUrlOff(String identityUrlOff) {
        this.identityUrlOff = identityUrlOff;
    }

    @Basic
    @Column(name = "identityHoldUrl")
    public String getIdentityHoldUrl() {
        return identityHoldUrl;
    }

    public void setIdentityHoldUrl(String identityHoldUrl) {
        this.identityHoldUrl = identityHoldUrl;
    }

    @Basic
    @Column(name = "validateVideoUrl")
    public String getValidateVideoUrl() {
        return validateVideoUrl;
    }

    public void setValidateVideoUrl(String validateVideoUrl) {
        this.validateVideoUrl = validateVideoUrl;
    }

    @Basic
    @Column(name = "auditTime")
    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "cancelReason")
    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditor")
    public Fadmin getFadmin() {
        return fadmin;
    }

    public void setFadmin(Fadmin fadmin) {
        this.fadmin = fadmin;
    }

    @Column(name = "bankName")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "bankNumber")
    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fvalidatekyc that = (Fvalidatekyc) o;

        if (id != that.id) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (identityUrlOn != null ? !identityUrlOn.equals(that.identityUrlOn) : that.identityUrlOn != null)
            return false;
        if (identityUrlOff != null ? !identityUrlOff.equals(that.identityUrlOff) : that.identityUrlOff != null)
            return false;
        if (identityHoldUrl != null ? !identityHoldUrl.equals(that.identityHoldUrl) : that.identityHoldUrl != null)
            return false;
        if (validateVideoUrl != null ? !validateVideoUrl.equals(that.validateVideoUrl) : that.validateVideoUrl != null)
            return false;
        if (auditTime != null ? !auditTime.equals(that.auditTime) : that.auditTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (cancelReason != null ? !cancelReason.equals(that.cancelReason) : that.cancelReason != null) return false;

        return true;
    }

    @Transient
    public String getBankNumber_s() {
        return Utils.getAccount(bankNumber);
    }

    public void setBankNumber_s(String bankNumber_s) {
        this.bankNumber_s = bankNumber_s;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (identityUrlOn != null ? identityUrlOn.hashCode() : 0);
        result = 31 * result + (identityUrlOff != null ? identityUrlOff.hashCode() : 0);
        result = 31 * result + (identityHoldUrl != null ? identityHoldUrl.hashCode() : 0);
        result = 31 * result + (validateVideoUrl != null ? validateVideoUrl.hashCode() : 0);
        result = 31 * result + (auditTime != null ? auditTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (cancelReason != null ? cancelReason.hashCode() : 0);
        return result;
    }
}
