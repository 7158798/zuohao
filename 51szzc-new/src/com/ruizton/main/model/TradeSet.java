package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by luwei on 17-2-14.
 */
@Entity
@Table(name = "t_trade_set")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TradeSet {

    private Integer id;

    //用户id
    private Fuser fuser;

    //比例
    private BigDecimal ratio;

    //每天从其它平台买币的上限
    private BigDecimal upperLimit;


    private Timestamp createDate;
    private Timestamp modifiedDate;

    private Ftrademapping ftrademapping;

    //操作版本号
    private Integer version;

    //单笔的最大币数
    private BigDecimal singleNum;

    //未成交的订单笔数
    private Integer unTradeOrderNum;

    //手机号
    private String mobileNumber;

    //暂停时间，单位分钟
    private Integer pauseTime;

    // 发送错误短信后间隔时间，单位分钟
    private Integer pauseMsgTime;
    // 单笔最小的比特币
    private BigDecimal minSingleNum;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @Column(name = "ratio")
    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    @Column(name = "upper_limit")
    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "modified_date")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_mapping_id")
    public Ftrademapping getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Ftrademapping ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    @Column(name = "version")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @Column(name = "single_num")
    public BigDecimal getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(BigDecimal singleNum) {
        this.singleNum = singleNum;
    }

    @Column(name = "unTradeOrderNum")
    public Integer getUnTradeOrderNum() {
        return unTradeOrderNum;
    }

    public void setUnTradeOrderNum(Integer unTradeOrderNum) {
        this.unTradeOrderNum = unTradeOrderNum;
    }

    @Column(name = "mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(name = "pause_time")
    public Integer getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Integer pauseTime) {
        this.pauseTime = pauseTime;
    }

    @Column(name = "pause_msg_time")
    public Integer getPauseMsgTime() {
        return pauseMsgTime;
    }

    public void setPauseMsgTime(Integer pauseMsgTime) {
        this.pauseMsgTime = pauseMsgTime;
    }

    @Column(name = "min_single_num")
    public BigDecimal getMinSingleNum() {
        return minSingleNum;
    }

    public void setMinSingleNum(BigDecimal minSingleNum) {
        this.minSingleNum = minSingleNum;
    }
}
