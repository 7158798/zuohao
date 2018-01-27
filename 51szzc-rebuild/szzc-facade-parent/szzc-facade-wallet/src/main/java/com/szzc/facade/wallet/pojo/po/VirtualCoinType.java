package com.szzc.facade.wallet.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirtualCoinType extends BasePo implements Serializable {

    /**
     * 对应表字段为：fvirtualcointype.fId
     */
    private Long fid;

    /**
     * 名称，对应表字段为：fvirtualcointype.fName
     */
    private String fname;

    /**
     * 简称，对应表字段为：fvirtualcointype.fShortName
     */
    private String fshortname;

    /**
     * 描述，对应表字段为：fvirtualcointype.fDescription
     */
    private String fdescription;

    /**
     * 创建时间，对应表字段为：fvirtualcointype.fAddTime
     */
    private Date faddtime;

    /**
     * 状态:1正常,2禁用，对应表字段为：fvirtualcointype.fstatus
     */
    private Integer fstatus;

    /**
     * 对应表字段为：fvirtualcointype.version
     */
    private Integer version;

    /**
     * 符号，对应表字段为：fvirtualcointype.fSymbol
     */
    private String fsymbol;

    /**
     * 对应表字段为：fvirtualcointype.faccess_key
     */
    private String faccessKey;

    /**
     * 对应表字段为：fvirtualcointype.fsecrt_key
     */
    private String fsecrtKey;

    /**
     * ip地址，对应表字段为：fvirtualcointype.fip
     */
    private String fip;

    /**
     * 端口号，对应表字段为：fvirtualcointype.fport
     */
    private String fport;

    /**
     * 是否可以提现，对应表字段为：fvirtualcointype.FIsWithDraw
     */
    private Boolean fiswithdraw;

    /**
     * 对应表字段为：fvirtualcointype.furl
     */
    private String furl;

    /**
     * 对应表字段为：fvirtualcointype.fweburl
     */
    private String fweburl;

    /**
     * 虚拟币类型，是否法币：0是,2否，对应表字段为：fvirtualcointype.ftype
     */
    private Integer ftype;

    /**
     * 充值是否可以到帐，对应表字段为：fvirtualcointype.fisauto
     */
    private Byte fisauto;

    /**
     * 是否可以充值，对应表字段为：fvirtualcointype.fisrecharge
     */
    private Byte fisrecharge;

    /**
     * 对应表字段为：fvirtualcointype.fmaxqty
     */
    private BigDecimal fmaxqty;

    /**
     * 对应表字段为：fvirtualcointype.fminqty
     */
    private BigDecimal fminqty;

    /**
     * 对应表字段为：fvirtualcointype.isEth
     */
    private Boolean iseth;

    /**
     * 对应表字段为：fvirtualcointype.mainAddr
     */
    private String mainaddr;

    /**
     * 对应表字段为：fvirtualcointype.startBlockId
     */
    private Integer startblockid;

    /**
     * 密码，对应表字段为：fvirtualcointype.password
     */
    private String password;

    /**
     * 加密字符串，对应表字段为：fvirtualcointype.salt
     */
    private String salt;

    /**
     * 是否启用密码拆分，对应表字段为：fvirtualcointype.is_split_password
     */
    private Byte isSplitPassword;

    /**
     * 对应表字段为：fvirtualcointype.furl2
     */
    private String furl2;

    /**
     * 系统自动提币，每日提现次数，对应表字段为：fvirtualcointype.auto_day_count
     */
    private Integer autoDayCount;

    /**
     * 系统自动提币，单笔额度，对应表字段为：fvirtualcointype.auto_single_limit
     */
    private BigDecimal autoSingleLimit;

    /**
     * 系统自动提币，每日提现额度，对应表字段为：fvirtualcointype.auto_day_limit
     */
    private BigDecimal autoDayLimit;

    /**
     * 系统自动提币时，输入的密码(如果有拆分，则存储拆分的那一半)，对应表字段为：fvirtualcointype.auto_password
     */
    private String autoPassword;

    /**
     * kyc1每日提现次数，对应表字段为：fvirtualcointype.kyc1_day_count
     */
    private Integer kyc1DayCount;

    /**
     * kyc1单笔提现额度，对应表字段为：fvirtualcointype.kyc1_single_limit
     */
    private BigDecimal kyc1SingleLimit;

    /**
     * kyc1每日提现额度，对应表字段为：fvirtualcointype.kyc1_day_limit
     */
    private BigDecimal kyc1DayLimit;

    /**
     * kyc1每月提现额度，对应表字段为：fvirtualcointype.kyc1_month_limit
     */
    private BigDecimal kyc1MonthLimit;

    /**
     * kyc2每日提现次数，对应表字段为：fvirtualcointype.kyc2_day_count
     */
    private Integer kyc2DayCount;

    /**
     * kyc2单笔提现额度，对应表字段为：fvirtualcointype.kyc2_single_limit
     */
    private BigDecimal kyc2SingleLimit;

    /**
     * kyc2每日提现额度，对应表字段为：fvirtualcointype.kyc2_day_limit
     */
    private BigDecimal kyc2DayLimit;

    /**
     * kyc2每月提现额度，对应表字段为：fvirtualcointype.kyc2_month_limit
     */
    private BigDecimal kyc2MonthLimit;

    private static final long serialVersionUID = 1L;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getFshortname() {
        return fshortname;
    }

    public void setFshortname(String fshortname) {
        this.fshortname = fshortname == null ? null : fshortname.trim();
    }

    public String getFdescription() {
        return fdescription;
    }

    public void setFdescription(String fdescription) {
        this.fdescription = fdescription == null ? null : fdescription.trim();
    }

    public Date getFaddtime() {
        return faddtime;
    }

    public void setFaddtime(Date faddtime) {
        this.faddtime = faddtime;
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

    public String getFsymbol() {
        return fsymbol;
    }

    public void setFsymbol(String fsymbol) {
        this.fsymbol = fsymbol == null ? null : fsymbol.trim();
    }

    public String getFaccessKey() {
        return faccessKey;
    }

    public void setFaccessKey(String faccessKey) {
        this.faccessKey = faccessKey == null ? null : faccessKey.trim();
    }

    public String getFsecrtKey() {
        return fsecrtKey;
    }

    public void setFsecrtKey(String fsecrtKey) {
        this.fsecrtKey = fsecrtKey == null ? null : fsecrtKey.trim();
    }

    public String getFip() {
        return fip;
    }

    public void setFip(String fip) {
        this.fip = fip == null ? null : fip.trim();
    }

    public String getFport() {
        return fport;
    }

    public void setFport(String fport) {
        this.fport = fport == null ? null : fport.trim();
    }

    public Boolean getFiswithdraw() {
        return fiswithdraw;
    }

    public void setFiswithdraw(Boolean fiswithdraw) {
        this.fiswithdraw = fiswithdraw;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl == null ? null : furl.trim();
    }

    public String getFweburl() {
        return fweburl;
    }

    public void setFweburl(String fweburl) {
        this.fweburl = fweburl == null ? null : fweburl.trim();
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public Byte getFisauto() {
        return fisauto;
    }

    public void setFisauto(Byte fisauto) {
        this.fisauto = fisauto;
    }

    public Byte getFisrecharge() {
        return fisrecharge;
    }

    public void setFisrecharge(Byte fisrecharge) {
        this.fisrecharge = fisrecharge;
    }

    public BigDecimal getFmaxqty() {
        return fmaxqty;
    }

    public void setFmaxqty(BigDecimal fmaxqty) {
        this.fmaxqty = fmaxqty;
    }

    public BigDecimal getFminqty() {
        return fminqty;
    }

    public void setFminqty(BigDecimal fminqty) {
        this.fminqty = fminqty;
    }

    public Boolean getIseth() {
        return iseth;
    }

    public void setIseth(Boolean iseth) {
        this.iseth = iseth;
    }

    public String getMainaddr() {
        return mainaddr;
    }

    public void setMainaddr(String mainaddr) {
        this.mainaddr = mainaddr == null ? null : mainaddr.trim();
    }

    public Integer getStartblockid() {
        return startblockid;
    }

    public void setStartblockid(Integer startblockid) {
        this.startblockid = startblockid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Byte getIsSplitPassword() {
        return isSplitPassword;
    }

    public void setIsSplitPassword(Byte isSplitPassword) {
        this.isSplitPassword = isSplitPassword;
    }

    public String getFurl2() {
        return furl2;
    }

    public void setFurl2(String furl2) {
        this.furl2 = furl2 == null ? null : furl2.trim();
    }

    public Integer getAutoDayCount() {
        return autoDayCount;
    }

    public void setAutoDayCount(Integer autoDayCount) {
        this.autoDayCount = autoDayCount;
    }

    public BigDecimal getAutoSingleLimit() {
        return autoSingleLimit;
    }

    public void setAutoSingleLimit(BigDecimal autoSingleLimit) {
        this.autoSingleLimit = autoSingleLimit;
    }

    public BigDecimal getAutoDayLimit() {
        return autoDayLimit;
    }

    public void setAutoDayLimit(BigDecimal autoDayLimit) {
        this.autoDayLimit = autoDayLimit;
    }

    public String getAutoPassword() {
        return autoPassword;
    }

    public void setAutoPassword(String autoPassword) {
        this.autoPassword = autoPassword == null ? null : autoPassword.trim();
    }

    public Integer getKyc1DayCount() {
        return kyc1DayCount;
    }

    public void setKyc1DayCount(Integer kyc1DayCount) {
        this.kyc1DayCount = kyc1DayCount;
    }

    public BigDecimal getKyc1SingleLimit() {
        return kyc1SingleLimit;
    }

    public void setKyc1SingleLimit(BigDecimal kyc1SingleLimit) {
        this.kyc1SingleLimit = kyc1SingleLimit;
    }

    public BigDecimal getKyc1DayLimit() {
        return kyc1DayLimit;
    }

    public void setKyc1DayLimit(BigDecimal kyc1DayLimit) {
        this.kyc1DayLimit = kyc1DayLimit;
    }

    public BigDecimal getKyc1MonthLimit() {
        return kyc1MonthLimit;
    }

    public void setKyc1MonthLimit(BigDecimal kyc1MonthLimit) {
        this.kyc1MonthLimit = kyc1MonthLimit;
    }

    public Integer getKyc2DayCount() {
        return kyc2DayCount;
    }

    public void setKyc2DayCount(Integer kyc2DayCount) {
        this.kyc2DayCount = kyc2DayCount;
    }

    public BigDecimal getKyc2SingleLimit() {
        return kyc2SingleLimit;
    }

    public void setKyc2SingleLimit(BigDecimal kyc2SingleLimit) {
        this.kyc2SingleLimit = kyc2SingleLimit;
    }

    public BigDecimal getKyc2DayLimit() {
        return kyc2DayLimit;
    }

    public void setKyc2DayLimit(BigDecimal kyc2DayLimit) {
        this.kyc2DayLimit = kyc2DayLimit;
    }

    public BigDecimal getKyc2MonthLimit() {
        return kyc2MonthLimit;
    }

    public void setKyc2MonthLimit(BigDecimal kyc2MonthLimit) {
        this.kyc2MonthLimit = kyc2MonthLimit;
    }
}