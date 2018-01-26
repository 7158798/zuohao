package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 价格推送
 * Created by zygong on 17-4-11.
 */
@Entity
@Table(name = "fapppush")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fapppush implements java.io.Serializable{
    private int fid;
    private int fuser; //用户
    private int fispush; //1：不推送  2：推送
    private int fissms; //1：不发送短信  2：发送短信
    private int fcointype; //币种类别
    private String fphonecode; //手机设备码
    private double fhighprice; //最高价
    private double flowprice; //最低价
    private int fphonetype; //1：android 2：ios
    private Timestamp flastsendtime; // 最新一次发送时间

    public Fapppush() {
    }

    public Fapppush(int fid, int fuser, int fispush, int fissms, int fcointype, String fphonecode, double fhighprice, double flowprice, int fphonetype, Timestamp flastsendtime) {
        this.fid = fid;
        this.fuser = fuser;
        this.fispush = fispush;
        this.fissms = fissms;
        this.fcointype = fcointype;
        this.fphonecode = fphonecode;
        this.fhighprice = fhighprice;
        this.flowprice = flowprice;
        this.fphonetype = fphonetype;
        this.flastsendtime = flastsendtime;
    }

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid", unique = true, nullable = false)
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    @Column(name = "fuser")
    public int getFuser() {
        return fuser;
    }

    public void setFuser(int fuser) {
        this.fuser = fuser;
    }

    @Column(name = "fispush")
    public int getFispush() {
        return fispush;
    }

    public void setFispush(int fispush) {
        this.fispush = fispush;
    }

    @Column(name = "fissms")
    public int getFissms() {
        return fissms;
    }

    public void setFissms(int fissms) {
        this.fissms = fissms;
    }

    @Column(name = "fcointype")
    public int getFcointype() {
        return fcointype;
    }

    public void setFcointype(int fcointype) {
        this.fcointype = fcointype;
    }

    @Column(name = "fphonecode")
    public String getFphonecode() {
        return fphonecode;
    }

    public void setFphonecode(String fphonecode) {
        this.fphonecode = fphonecode;
    }

    @Column(name = "fhighprice")
    public double getFhighprice() {
        return fhighprice;
    }

    public void setFhighprice(double fhighprice) {
        this.fhighprice = fhighprice;
    }

    @Column(name = "flowprice")
    public double getFlowprice() {
        return flowprice;
    }

    public void setFlowprice(double flowprice) {
        this.flowprice = flowprice;
    }

    @Column(name = "fphonetype")
    public int getFphonetype() {
        return fphonetype;
    }

    public void setFphonetype(int fphonetype) {
        this.fphonetype = fphonetype;
    }

    @Column(name = "flastsendtime")
    public Timestamp getFlastsendtime() {
        return flastsendtime;
    }

    public void setFlastsendtime(Timestamp flastsendtime) {
        this.flastsendtime = flastsendtime;
    }
}
