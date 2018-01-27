package com.szzc.facade.fentrustLog.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FentrustLog extends BasePo implements Serializable {

    /**
     * 主键，对应表字段为：fentrustlog.fid
     */
    private Integer fid;

    /**
     * 委托交易记录表id，对应表字段为：fentrustlog.FEn_fId
     */
    private Integer fenFid;

    /**
     * 交易金额，对应表字段为：fentrustlog.fAmount
     */
    private BigDecimal famount;

    /**
     * 创建时间，对应表字段为：fentrustlog.fCreateTime
     */
    private Date fcreatetime;

    /**
     * 单价，对应表字段为：fentrustlog.fPrize
     */
    private BigDecimal fprize;

    /**
     * 数量，对应表字段为：fentrustlog.fCount
     */
    private BigDecimal fcount;

    /**
     * 对应表字段为：fentrustlog.version
     */
    private Integer version;

    /**
     * 对应表字段为：fentrustlog.isactive
     */
    private Boolean isactive;

    /**
     * 对应表字段为：fentrustlog.ftrademapping
     */
    private Integer ftrademapping;

    /**
     * 对应表字段为：fentrustlog.fEntrustType
     */
    private Integer fentrusttype;

    /**
     * 手续费，对应表字段为：fentrustlog.ffees
     */
    private BigDecimal ffees;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFenFid() {
        return fenFid;
    }

    public void setFenFid(Integer fenFid) {
        this.fenFid = fenFid;
    }

    public BigDecimal getFamount() {
        return famount;
    }

    public void setFamount(BigDecimal famount) {
        this.famount = famount;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public BigDecimal getFprize() {
        return fprize;
    }

    public void setFprize(BigDecimal fprize) {
        this.fprize = fprize;
    }

    public BigDecimal getFcount() {
        return fcount;
    }

    public void setFcount(BigDecimal fcount) {
        this.fcount = fcount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public Integer getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Integer ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    public Integer getFentrusttype() {
        return fentrusttype;
    }

    public void setFentrusttype(Integer fentrusttype) {
        this.fentrusttype = fentrusttype;
    }

    public BigDecimal getFfees() {
        return ffees;
    }

    public void setFfees(BigDecimal ffees) {
        this.ffees = ffees;
    }
}