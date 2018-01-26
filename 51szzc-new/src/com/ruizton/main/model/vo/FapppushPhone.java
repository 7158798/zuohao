package com.ruizton.main.model.vo;

import com.ruizton.main.model.Fapppush;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zygong on 17-4-14.
 */
public class FapppushPhone{

    private int fid;
    private int fuser; //用户
    private int fispush; //1：不推送  2：推送
    private int fissms; //1：不发送短信  2：发送短信
    private int fcointype; //币种类别
    private String fphonecode; //手机设备码
    private double fhighprice; //最高价
    private double flowprice; //最低价
    private int fphonetype; //1：android 2：ios
    private String ftelephone; //手机号

    public FapppushPhone(int fid, int fuser, int fispush, int fissms, int fcointype, String fphonecode, double fhighprice, double flowprice, int fphonetype, String ftelephone) {
        this.fid = fid;
        this.fuser = fuser;
        this.fispush = fispush;
        this.fissms = fissms;
        this.fcointype = fcointype;
        this.fphonecode = fphonecode;
        this.fhighprice = fhighprice;
        this.flowprice = flowprice;
        this.fphonetype = fphonetype;
        this.ftelephone = ftelephone;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFuser() {
        return fuser;
    }

    public void setFuser(int fuser) {
        this.fuser = fuser;
    }

    public int getFispush() {
        return fispush;
    }

    public void setFispush(int fispush) {
        this.fispush = fispush;
    }

    public int getFissms() {
        return fissms;
    }

    public void setFissms(int fissms) {
        this.fissms = fissms;
    }

    public int getFcointype() {
        return fcointype;
    }

    public void setFcointype(int fcointype) {
        this.fcointype = fcointype;
    }

    public String getFphonecode() {
        return fphonecode;
    }

    public void setFphonecode(String fphonecode) {
        this.fphonecode = fphonecode;
    }

    public double getFhighprice() {
        return fhighprice;
    }

    public void setFhighprice(double fhighprice) {
        this.fhighprice = fhighprice;
    }

    public double getFlowprice() {
        return flowprice;
    }

    public void setFlowprice(double flowprice) {
        this.flowprice = flowprice;
    }

    public int getFphonetype() {
        return fphonetype;
    }

    public void setFphonetype(int fphonetype) {
        this.fphonetype = fphonetype;
    }

    public String getFtelephone() {
        return ftelephone;
    }

    public void setFtelephone(String ftelephone) {
        this.ftelephone = ftelephone;
    }
}
