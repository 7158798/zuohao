package com.szzc.facade.wallet.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirtualCapitaloperation extends BasePo implements Serializable {

    /**
     * 对应表字段为：fvirtualcaptualoperation.fId
     */
    private Integer fid;

    /**
     * 用户id，对应表字段为：fvirtualcaptualoperation.FUs_fId2
     */
    private Integer fusFid2;

    /**
     * 货币类型id，对应表字段为：fvirtualcaptualoperation.fVi_fId2
     */
    private Integer fviFid2;

    /**
     * 创建日期，对应表字段为：fvirtualcaptualoperation.fCreateTime
     */
    private Date fcreatetime;

    /**
     * 数量，对应表字段为：fvirtualcaptualoperation.fAmount
     */
    private BigDecimal famount;

    /**
     * 手续费，对应表字段为：fvirtualcaptualoperation.ffees
     */
    private BigDecimal ffees;

    /**
     * 类型:1虚拟币充值,2虚拟币提现，对应表字段为：fvirtualcaptualoperation.fType
     */
    private Integer ftype;

    /**
     * 状态１:等待提现 3.提现成功  4.用户撤销，对应表字段为：fvirtualcaptualoperation.fStatus
     */
    private Integer fstatus;

    /**
     * 上次更新时间，对应表字段为：fvirtualcaptualoperation.flastUpdateTime
     */
    private Date flastupdatetime;

    /**
     * 对应表字段为：fvirtualcaptualoperation.version
     */
    private Integer version;

    /**
     * 提现地址，对应表字段为：fvirtualcaptualoperation.withdraw_virtual_address
     */
    private String withdrawVirtualAddress;

    /**
     * 对应表字段为：fvirtualcaptualoperation.recharge_virtual_address
     */
    private String rechargeVirtualAddress;

    /**
     * 钱包交易订单号，对应表字段为：fvirtualcaptualoperation.ftradeUniqueNumber
     */
    private String ftradeuniquenumber;

    /**
     * 对应表字段为：fvirtualcaptualoperation.fconfirmations
     */
    private Integer fconfirmations;

    /**
     * 对应表字段为：fvirtualcaptualoperation.fhasOwner
     */
    private Boolean fhasowner;

    /**
     * 对应表字段为：fvirtualcaptualoperation.fischarge
     */
    private String fischarge;

    /**
     * 对应表字段为：fvirtualcaptualoperation.cancelReason
     */
    private String cancelreason;

    /**
     * 是否合同充值，对应表字段为：fvirtualcaptualoperation.is_contract
     */
    private Boolean isContract;

    /**
     * 对应表字段为：fvirtualcaptualoperation.mailLinkVerDate
     */
    private Date maillinkverdate;

    /**
     * 对应表字段为：fvirtualcaptualoperation.mailLinkUUID
     */
    private String maillinkuuid;

    /**
     * 对应表字段为：fvirtualcaptualoperation.autoProcess
     */
    private Boolean autoprocess;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFusFid2() {
        return fusFid2;
    }

    public void setFusFid2(Integer fusFid2) {
        this.fusFid2 = fusFid2;
    }

    public Integer getFviFid2() {
        return fviFid2;
    }

    public void setFviFid2(Integer fviFid2) {
        this.fviFid2 = fviFid2;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public BigDecimal getFamount() {
        return famount;
    }

    public void setFamount(BigDecimal famount) {
        this.famount = famount;
    }

    public BigDecimal getFfees() {
        return ffees;
    }

    public void setFfees(BigDecimal ffees) {
        this.ffees = ffees;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getWithdrawVirtualAddress() {
        return withdrawVirtualAddress;
    }

    public void setWithdrawVirtualAddress(String withdrawVirtualAddress) {
        this.withdrawVirtualAddress = withdrawVirtualAddress == null ? null : withdrawVirtualAddress.trim();
    }

    public String getRechargeVirtualAddress() {
        return rechargeVirtualAddress;
    }

    public void setRechargeVirtualAddress(String rechargeVirtualAddress) {
        this.rechargeVirtualAddress = rechargeVirtualAddress == null ? null : rechargeVirtualAddress.trim();
    }

    public String getFtradeuniquenumber() {
        return ftradeuniquenumber;
    }

    public void setFtradeuniquenumber(String ftradeuniquenumber) {
        this.ftradeuniquenumber = ftradeuniquenumber == null ? null : ftradeuniquenumber.trim();
    }

    public Integer getFconfirmations() {
        return fconfirmations;
    }

    public void setFconfirmations(Integer fconfirmations) {
        this.fconfirmations = fconfirmations;
    }

    public Boolean getFhasowner() {
        return fhasowner;
    }

    public void setFhasowner(Boolean fhasowner) {
        this.fhasowner = fhasowner;
    }

    public String getFischarge() {
        return fischarge;
    }

    public void setFischarge(String fischarge) {
        this.fischarge = fischarge == null ? null : fischarge.trim();
    }

    public String getCancelreason() {
        return cancelreason;
    }

    public void setCancelreason(String cancelreason) {
        this.cancelreason = cancelreason == null ? null : cancelreason.trim();
    }

    public Boolean getIsContract() {
        return isContract;
    }

    public void setIsContract(Boolean isContract) {
        this.isContract = isContract;
    }

    public Date getMaillinkverdate() {
        return maillinkverdate;
    }

    public void setMaillinkverdate(Date maillinkverdate) {
        this.maillinkverdate = maillinkverdate;
    }

    public String getMaillinkuuid() {
        return maillinkuuid;
    }

    public void setMaillinkuuid(String maillinkuuid) {
        this.maillinkuuid = maillinkuuid == null ? null : maillinkuuid.trim();
    }

    public Boolean getAutoprocess() {
        return autoprocess;
    }

    public void setAutoprocess(Boolean autoprocess) {
        this.autoprocess = autoprocess;
    }
}