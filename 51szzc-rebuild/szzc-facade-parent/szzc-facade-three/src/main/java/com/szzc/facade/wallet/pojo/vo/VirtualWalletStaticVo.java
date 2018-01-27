package com.szzc.facade.wallet.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.math.BigDecimal;

/**
 * Created by lx on 17-5-25.
 */
public class VirtualWalletStaticVo extends BasePageVo {

    //主键流水号
    private Integer fid;

    //虚拟币类型id  对应表fvirtualcointype.fid
    private Integer fVi_fId;

    //全平台账户可用总和
    private BigDecimal fable;

    //全平台账户冻结总和
    private BigDecimal ffrozen;

    //全平台账户余额总和
    private BigDecimal balance;


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getfVi_fId() {
        return fVi_fId;
    }

    public void setfVi_fId(Integer fVi_fId) {
        this.fVi_fId = fVi_fId;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
