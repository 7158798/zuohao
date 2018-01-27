package com.szzc.facade.ftrademapping.pojo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class FtrademappingDto implements Serializable {

    /**
     * 对应表字段为：ftrademapping.fid
     */
    private Integer fid;

    /**
     * 法币类型，对应表字段为：ftrademapping.fvirtualcointype1
     */
    private Integer fvirtualcointype1;

    /**
     * 交易币类型，对应表字段为：ftrademapping.fvirtualcointype2
     */
    private Integer fvId;

    /**
     * 交易时间，对应表字段为：ftrademapping.ftradeTime
     */
    private String ftradetime;

    /**
     * 对应表字段为：ftrademapping.fisLimit
     */
    private Boolean fislimit;

    /**
     * 对应表字段为：ftrademapping.fstatus
     */
    private Integer fstatus;

    /**
     * 对应表字段为：ftrademapping.version
     */
    private Integer version;

    /**
     * 单价小数位，对应表字段为：ftrademapping.fcount1
     */
    private Integer fcount1;

    /**
     * 数量小数位，对应表字段为：ftrademapping.fcount2
     */
    private Integer fcount2;

    /**
     * 最小挂单数量，对应表字段为：ftrademapping.fminBuyCount
     */
    private BigDecimal fminbuycount;

    /**
     * 最小挂单单价，对应表字段为：ftrademapping.fminBuyPrice
     */
    private BigDecimal fminbuyprice;

    /**
     * 最小挂单金额，对应表字段为：ftrademapping.fminBuyAmount
     */
    private BigDecimal fminbuyamount;

    /**
     * 开盘价，对应表字段为：ftrademapping.fprice
     */
    private BigDecimal fprice;

    private String fname;

    private String fshortName;

    private Integer ftype;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFvirtualcointype1() {
        return fvirtualcointype1;
    }

    public void setFvirtualcointype1(Integer fvirtualcointype1) {
        this.fvirtualcointype1 = fvirtualcointype1;
    }

    public String getFtradetime() {
        return ftradetime;
    }

    public void setFtradetime(String ftradetime) {
        this.ftradetime = ftradetime == null ? null : ftradetime.trim();
    }

    public Boolean getFislimit() {
        return fislimit;
    }

    public void setFislimit(Boolean fislimit) {
        this.fislimit = fislimit;
    }

    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getFcount1() {
        return fcount1;
    }

    public void setFcount1(Integer fcount1) {
        this.fcount1 = fcount1;
    }

    public Integer getFcount2() {
        return fcount2;
    }

    public void setFcount2(Integer fcount2) {
        this.fcount2 = fcount2;
    }

    public BigDecimal getFminbuycount() {
        return fminbuycount;
    }

    public void setFminbuycount(BigDecimal fminbuycount) {
        this.fminbuycount = fminbuycount;
    }

    public BigDecimal getFminbuyprice() {
        return fminbuyprice;
    }

    public void setFminbuyprice(BigDecimal fminbuyprice) {
        this.fminbuyprice = fminbuyprice;
    }

    public BigDecimal getFminbuyamount() {
        return fminbuyamount;
    }

    public void setFminbuyamount(BigDecimal fminbuyamount) {
        this.fminbuyamount = fminbuyamount;
    }

    public BigDecimal getFprice() {
        return fprice;
    }

    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    public Integer getFvId() {
        return fvId;
    }

    public void setFvId(Integer fvId) {
        this.fvId = fvId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFshortName() {
        return fshortName;
    }

    public void setFshortName(String fshortName) {
        this.fshortName = fshortName;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }
}