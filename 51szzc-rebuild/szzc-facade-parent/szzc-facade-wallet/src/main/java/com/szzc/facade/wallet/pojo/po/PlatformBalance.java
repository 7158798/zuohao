package com.szzc.facade.wallet.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PlatformBalance extends BasePo implements Serializable {

    /**
     * 主键流水号，对应表字段为：fplatform_balance.fid
     */
    private Integer fid;

    /**
     * 虚拟币类型id，对应表字段为：fplatform_balance.fvi_id
     */
    private Integer fviId;

    /**
     * 平台账户上可用的余额，对应表字段为：fplatform_balance.fable
     */
    private BigDecimal fable;

    /**
     * 平台账户上冻结的余额，对应表字段为：fplatform_balance.ffrozen
     */
    private BigDecimal ffrozen;

    /**
     * 总余额(含冻结)，对应表字段为：fplatform_balance.ftotal
     */
    private BigDecimal ftotal;

    /**
     * 创建时间，对应表字段为：fplatform_balance.fcreateTime
     */
    private Date fcreatetime;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFviId() {
        return fviId;
    }

    public void setFviId(Integer fviId) {
        this.fviId = fviId;
    }

    public BigDecimal getFable() {
        return fable;
    }

    public void setFable(BigDecimal fable) {
        this.fable = fable;
    }

    public BigDecimal getFfrozen() {
        return ffrozen;
    }

    public void setFfrozen(BigDecimal ffrozen) {
        this.ffrozen = ffrozen;
    }

    public BigDecimal getFtotal() {
        return ftotal;
    }

    public void setFtotal(BigDecimal ftotal) {
        this.ftotal = ftotal;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }
}