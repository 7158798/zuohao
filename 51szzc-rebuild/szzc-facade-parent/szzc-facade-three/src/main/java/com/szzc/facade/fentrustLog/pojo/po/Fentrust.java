package com.szzc.facade.fentrustLog.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Fentrust extends BasePo implements Serializable {

    /**
     * 对应表字段为：fentrust.fId
     */
    private Integer fid;

    /**
     * 用户id，对应表字段为：fentrust.FUs_fId
     */
    private Integer fusFid;

    /**
     * 法币交易类型id，对应表字段为：fentrust.ftrademapping
     */
    private Integer ftrademapping;

    /**
     * 创建日期，对应表字段为：fentrust.fCreateTime
     */
    private Date fcreatetime;

    /**
     * 类型:0买入,1卖出，对应表字段为：fentrust.fEntrustType
     */
    private Integer fentrusttype;

    /**
     * 单价设定的买入或卖出价格，对应表字段为：fentrust.fPrize
     */
    private BigDecimal fprize;

    /**
     * 总金额，对应表字段为：fentrust.fAmount
     */
    private BigDecimal famount;

    /**
     * 成功交易的单价，对应表字段为：fentrust.fsuccessAmount
     */
    private BigDecimal fsuccessamount;

    /**
     * 委托数量，对应表字段为：fentrust.fCount
     */
    private BigDecimal fcount;

    /**
     * 未成交数量，对应表字段为：fentrust.fleftCount
     */
    private BigDecimal fleftcount;

    /**
     * 状态:1未完成,2部分成交,3完全成交,4用户撤销，对应表字段为：fentrust.fStatus
     */
    private Integer fstatus;

    /**
     * 上次更新时间，对应表字段为：fentrust.flastUpdatTime
     */
    private Date flastupdattime;

    /**
     * 对应表字段为：fentrust.version
     */
    private Integer version;

    /**
     * 对应表字段为：fentrust.fisLimit
     */
    private Boolean fislimit;

    /**
     * 总手续费，对应表字段为：fentrust.ffees
     */
    private BigDecimal ffees;

    /**
     * 对应表字段为：fentrust.fleftfees
     */
    private BigDecimal fleftfees;

    /**
     * 对应表字段为：fentrust.fhasSubscription
     */
    private Byte fhassubscription;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFusFid() {
        return fusFid;
    }

    public void setFusFid(Integer fusFid) {
        this.fusFid = fusFid;
    }

    public Integer getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Integer ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Integer getFentrusttype() {
        return fentrusttype;
    }

    public void setFentrusttype(Integer fentrusttype) {
        this.fentrusttype = fentrusttype;
    }

    public BigDecimal getFprize() {
        return fprize;
    }

    public void setFprize(BigDecimal fprize) {
        this.fprize = fprize;
    }

    public BigDecimal getFamount() {
        return famount;
    }

    public void setFamount(BigDecimal famount) {
        this.famount = famount;
    }

    public BigDecimal getFsuccessamount() {
        return fsuccessamount;
    }

    public void setFsuccessamount(BigDecimal fsuccessamount) {
        this.fsuccessamount = fsuccessamount;
    }

    public BigDecimal getFcount() {
        return fcount;
    }

    public void setFcount(BigDecimal fcount) {
        this.fcount = fcount;
    }

    public BigDecimal getFleftcount() {
        return fleftcount;
    }

    public void setFleftcount(BigDecimal fleftcount) {
        this.fleftcount = fleftcount;
    }

    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    public Date getFlastupdattime() {
        return flastupdattime;
    }

    public void setFlastupdattime(Date flastupdattime) {
        this.flastupdattime = flastupdattime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getFislimit() {
        return fislimit;
    }

    public void setFislimit(Boolean fislimit) {
        this.fislimit = fislimit;
    }

    public BigDecimal getFfees() {
        return ffees;
    }

    public void setFfees(BigDecimal ffees) {
        this.ffees = ffees;
    }

    public BigDecimal getFleftfees() {
        return fleftfees;
    }

    public void setFleftfees(BigDecimal fleftfees) {
        this.fleftfees = fleftfees;
    }

    public Byte getFhassubscription() {
        return fhassubscription;
    }

    public void setFhassubscription(Byte fhassubscription) {
        this.fhassubscription = fhassubscription;
    }
}