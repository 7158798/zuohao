package com.szzc.facade.wallet.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class VirtualAddress extends BasePo implements Serializable {

    /**
     * 对应表字段为：fvirtualaddress.fId
     */
    private Integer fid;

    /**
     * 货币类型id，对应表字段为：fvirtualaddress.fVi_fId
     */
    private Integer fviFid;

    /**
     * 地址，对应表字段为：fvirtualaddress.fAdderess
     */
    private String fadderess;

    /**
     * 用户id，对应表字段为：fvirtualaddress.fuid
     */
    private Integer fuid;

    /**
     * 创建日期，对应表字段为：fvirtualaddress.fCreateTime
     */
    private Date fcreatetime;

    /**
     * 对应表字段为：fvirtualaddress.version
     */
    private Integer version;

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

    public String getFadderess() {
        return fadderess;
    }

    public void setFadderess(String fadderess) {
        this.fadderess = fadderess == null ? null : fadderess.trim();
    }

    public Integer getFuid() {
        return fuid;
    }

    public void setFuid(Integer fuid) {
        this.fuid = fuid;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}