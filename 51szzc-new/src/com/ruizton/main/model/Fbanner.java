package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * banner横幅广告管理
 * Created by luwei on 17-3-7.
 */
@Entity
@Table(name = "fbanner")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fbanner implements Serializable {

    private Integer fid;

    private Integer fseat;

    private String fimgurl;

    private String flinkurl;

    private Timestamp fstartDate;

    private Timestamp fendDate;

    private Integer fstatus;

    private Timestamp fcreateTime;

    private Fadmin  fcreateUser;

    private Integer fpriority;


    /*************非表字段 start******************/
    private String fseatName;

    private String fstatusName;

    //有效期
    private String fvalidate;

    //传递排序参数标识
    private String orderType;

    /*************非表字段 end******************/

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid",unique = true, nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Column(name="fseat")
    public Integer getFseat() {
        return fseat;
    }

    public void setFseat(Integer fseat) {
        this.fseat = fseat;
    }

    @Column(name="fimgurl")
    public String getFimgurl() {
        return fimgurl;
    }

    public void setFimgurl(String fimgurl) {
        this.fimgurl = fimgurl;
    }

    @Column(name="flinkurl")
    public String getFlinkurl() {
        return flinkurl;
    }

    public void setFlinkurl(String flinkurl) {
        this.flinkurl = flinkurl;
    }

    @Column(name="fstartDate")
    public Timestamp getFstartDate() {
        return fstartDate;
    }

    public void setFstartDate(Timestamp fstartDate) {
        this.fstartDate = fstartDate;
    }

    @Column(name="fendDate")
    public Timestamp getFendDate() {
        return fendDate;
    }

    public void setFendDate(Timestamp fendDate) {
        this.fendDate = fendDate;
    }

    @Column(name="fstatus")
    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    @Column(name = "fcreateTime")
    public Timestamp getFcreateTime() {
        return fcreateTime;
    }

    public void setFcreateTime(Timestamp fcreateTime) {
        this.fcreateTime = fcreateTime;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fcreateUser")
    public Fadmin getFcreateUser() {
        return fcreateUser;
    }

    public void setFcreateUser(Fadmin fcreateUser) {
        this.fcreateUser = fcreateUser;
    }


    @Transient
    public String getFseatName() {
        return fseatName;
    }

    public void setFseatName(String fseatName) {
        this.fseatName = fseatName;
    }

    @Transient
    public String getFstatusName() {
        return fstatusName;
    }

    public void setFstatusName(String fstatusName) {
        this.fstatusName = fstatusName;
    }

    @Transient
    public String getFvalidate() {
        return fvalidate;
    }

    public void setFvalidate(String fvalidate) {
        this.fvalidate = fvalidate;
    }

    @Transient
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "fpriority")
    public Integer getFpriority() {
        return fpriority;
    }

    public void setFpriority(Integer fpriority) {
        this.fpriority = fpriority;
    }
}
