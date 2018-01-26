package com.ruizton.main.model.robot;

import com.ruizton.main.Enum.robot.RobotTypeEnum;
import com.ruizton.main.model.Ftrademapping;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lx on 17-5-4.
 */
@Entity
@Table(name = "t_entrust_warn")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntrustWarn {

    private Integer id;

    private Ftrademapping ftrademapping;

    private String type;

    private String type_s;

    private BigDecimal mergeAmount;

    private BigDecimal singleAmount;

    private Integer waitMinute;

    private String mobileNumber;

    private Date createDate;

    private Date modifiedDate;

    private Integer version;

    private Integer pauseMsgTime;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id",unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_mapping_id")
    public Ftrademapping getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Ftrademapping ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Transient
    public String getType_s() {
        return RobotTypeEnum.getDescByCode(type);
    }

    public void setType_s(String type_s) {
        this.type_s = type_s;
    }

    @Column(name = "merge_amount")
    public BigDecimal getMergeAmount() {
        return mergeAmount;
    }

    public void setMergeAmount(BigDecimal mergeAmount) {
        this.mergeAmount = mergeAmount;
    }

    @Column(name = "single_amount")
    public BigDecimal getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(BigDecimal singleAmount) {
        this.singleAmount = singleAmount;
    }

    @Column(name = "wait_minute")
    public Integer getWaitMinute() {
        return waitMinute;
    }

    public void setWaitMinute(Integer waitMinute) {
        this.waitMinute = waitMinute;
    }

    @Column(name = "mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "modified_date")
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "pause_msg_time")
    public Integer getPauseMsgTime() {
        return pauseMsgTime;
    }

    public void setPauseMsgTime(Integer pauseMsgTime) {
        this.pauseMsgTime = pauseMsgTime;
    }
}
