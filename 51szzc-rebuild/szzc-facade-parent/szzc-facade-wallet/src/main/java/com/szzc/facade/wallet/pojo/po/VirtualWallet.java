package com.szzc.facade.wallet.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirtualWallet extends BasePo implements Serializable {

    /**
     * 对应表字段为：fvirtualwallet.fId
     */
    private Integer fid;

    /**
     * 货币类型id(fvirtualcointype的id)，对应表字段为：fvirtualwallet.fVi_fId
     */
    private Integer fviFid;

    /**
     * 总数量，对应表字段为：fvirtualwallet.fTotal
     */
    private BigDecimal ftotal;

    /**
     * 冻结数量，对应表字段为：fvirtualwallet.fFrozen
     */
    private BigDecimal ffrozen;

    /**
     * 更新时间，对应表字段为：fvirtualwallet.fLastUpdateTime
     */
    private Date flastupdatetime;

    /**
     * 用户id，对应表字段为：fvirtualwallet.fuid
     */
    private Integer fuid;

    /**
     * 对应表字段为：fvirtualwallet.fBorrowBtc
     */
    private BigDecimal fborrowbtc;

    /**
     * 对应表字段为：fvirtualwallet.fCanlendBtc
     */
    private BigDecimal fcanlendbtc;

    /**
     * 对应表字段为：fvirtualwallet.fFrozenLendBtc
     */
    private BigDecimal ffrozenlendbtc;

    /**
     * 对应表字段为：fvirtualwallet.fAlreadyLendBtc
     */
    private BigDecimal falreadylendbtc;

    /**
     * 对应表字段为：fvirtualwallet.version
     */
    private Integer version;

    /**
     * 对应表字段为：fvirtualwallet.fHaveAppointBorrowBtc
     */
    private BigDecimal fhaveappointborrowbtc;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFviFid() {
        return fviFid;
    }

    public void setFviFid(Integer fviFid) {
        this.fviFid = fviFid;
    }

    public BigDecimal getFtotal() {
        return ftotal;
    }

    public void setFtotal(BigDecimal ftotal) {
        this.ftotal = ftotal;
    }

    public BigDecimal getFfrozen() {
        return ffrozen;
    }

    public void setFfrozen(BigDecimal ffrozen) {
        this.ffrozen = ffrozen;
    }

    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    public Integer getFuid() {
        return fuid;
    }

    public void setFuid(Integer fuid) {
        this.fuid = fuid;
    }

    public BigDecimal getFborrowbtc() {
        return fborrowbtc;
    }

    public void setFborrowbtc(BigDecimal fborrowbtc) {
        this.fborrowbtc = fborrowbtc;
    }

    public BigDecimal getFcanlendbtc() {
        return fcanlendbtc;
    }

    public void setFcanlendbtc(BigDecimal fcanlendbtc) {
        this.fcanlendbtc = fcanlendbtc;
    }

    public BigDecimal getFfrozenlendbtc() {
        return ffrozenlendbtc;
    }

    public void setFfrozenlendbtc(BigDecimal ffrozenlendbtc) {
        this.ffrozenlendbtc = ffrozenlendbtc;
    }

    public BigDecimal getFalreadylendbtc() {
        return falreadylendbtc;
    }

    public void setFalreadylendbtc(BigDecimal falreadylendbtc) {
        this.falreadylendbtc = falreadylendbtc;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getFhaveappointborrowbtc() {
        return fhaveappointborrowbtc;
    }

    public void setFhaveappointborrowbtc(BigDecimal fhaveappointborrowbtc) {
        this.fhaveappointborrowbtc = fhaveappointborrowbtc;
    }
}