package com.ruizton.main.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.CoinTypeEnum;
import com.ruizton.main.Enum.VirtualCoinTypeStatusEnum;

/**
 * Fvirtualcointype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualcointype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvirtualcointype implements java.io.Serializable {

	// Fields

	private int fid;
	private int fid_s;
	// private boolean fisShare;// 是否可以交易
	private boolean FIsWithDraw;// 是否可以充值提现
	private String fname;
	private String fShortName;
	private String fdescription;
	private Timestamp faddTime;
	private int fstatus;// VirtualCoinTypeStatusEnum
	private String fstatus_s;
	private String fSymbol;
	private String faccess_key;
	private String fsecrt_key;
	private String fip;
	private String fport;
	private double lastDealPrize;// fake,最新成交价格
	private double higestBuyPrize;
	private double lowestSellPrize;
	private boolean canLend;// 是否可以借贷
	private Set<Fvirtualcaptualoperation> fvirtualcaptualoperations = new HashSet<Fvirtualcaptualoperation>(
			0);
	private Set<Fvirtualwallet> fvirtualwallets = new HashSet<Fvirtualwallet>(0);
	private int version;
	private String furl; // logo
	private String furl2; // 备用logo

	private String fweburl;

	// private int fcount;
	// private String ftradetime;
	// private double fprice;
	private Double total;

	// 虚拟币类型。是否法币
	private int ftype;
	private String ftype_s;

	private boolean fisauto;
	private boolean fisrecharge;
	private Double fmaxqty;
	private Double fminqty;
	
	private boolean fisEth ;
	private String mainAddr ;
	private int startBlockId ;
	private String password;
	private String salt;
	// 是否启用密码拆分
	private Boolean isSplitPassword;


	private int kyc1_day_count;

	private double kyc1_single_limit;

	private double kyc1_day_limit;

	private double kyc1_month_limit;


	private int kyc2_day_count;

	private double kyc2_single_limit;

	private double kyc2_day_limit;

	private double kyc2_month_limit;

	//系统自动提币，每日次数
	private Integer auto_day_count;

	//系统自动提币，单笔额度
	private BigDecimal auto_single_limit;

	//系统自动提币，每日提现额度
	private BigDecimal auto_day_limit;

	//系统自动提币，密码(如果有拆分，则存储拆分的那一半)
	private String auto_password;


	public int getKyc1_day_count() {
		return kyc1_day_count;
	}

	public void setKyc1_day_count(int kyc1_day_count) {
		this.kyc1_day_count = kyc1_day_count;
	}

	public double getKyc1_single_limit() {
		return kyc1_single_limit;
	}

	public void setKyc1_single_limit(double kyc1_single_limit) {
		this.kyc1_single_limit = kyc1_single_limit;
	}

	public double getKyc1_day_limit() {
		return kyc1_day_limit;
	}

	public void setKyc1_day_limit(double kyc1_day_limit) {
		this.kyc1_day_limit = kyc1_day_limit;
	}

	public double getKyc1_month_limit() {
		return kyc1_month_limit;
	}

	public void setKyc1_month_limit(double kyc1_month_limit) {
		this.kyc1_month_limit = kyc1_month_limit;
	}

	public int getKyc2_day_count() {
		return kyc2_day_count;
	}

	public void setKyc2_day_count(int kyc2_day_count) {
		this.kyc2_day_count = kyc2_day_count;
	}

	public double getKyc2_single_limit() {
		return kyc2_single_limit;
	}

	public void setKyc2_single_limit(double kyc2_single_limit) {
		this.kyc2_single_limit = kyc2_single_limit;
	}

	public double getKyc2_day_limit() {
		return kyc2_day_limit;
	}

	public void setKyc2_day_limit(double kyc2_day_limit) {
		this.kyc2_day_limit = kyc2_day_limit;
	}

	public double getKyc2_month_limit() {
		return kyc2_month_limit;
	}

	public void setKyc2_month_limit(double kyc2_month_limit) {
		this.kyc2_month_limit = kyc2_month_limit;
	}




	/** default constructor */
	public Fvirtualcointype() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fId", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Column(name = "fName", length = 32)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fDescription", length = 32)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fAddTime", length = 0)
	public Timestamp getFaddTime() {
		return this.faddTime;
	}

	public void setFaddTime(Timestamp faddTime) {
		this.faddTime = faddTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
	public Set<Fvirtualcaptualoperation> getFvirtualcaptualoperations() {
		return this.fvirtualcaptualoperations;
	}

	public void setFvirtualcaptualoperations(
			Set<Fvirtualcaptualoperation> fvirtualcaptualoperations) {
		this.fvirtualcaptualoperations = fvirtualcaptualoperations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fvirtualcointype")
	public Set<Fvirtualwallet> getFvirtualwallets() {
		return this.fvirtualwallets;
	}

	public void setFvirtualwallets(Set<Fvirtualwallet> fvirtualwallets) {
		this.fvirtualwallets = fvirtualwallets;
	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fShortName")
	public String getfShortName() {
		return fShortName;
	}

	public void setfShortName(String fShortName) {
		this.fShortName = fShortName;
	}

	@Transient
	public String getFstatus_s() {
		return VirtualCoinTypeStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Column(name = "fSymbol")
	public String getfSymbol() {
		return fSymbol;
	}

	public void setfSymbol(String fSymbol) {
		this.fSymbol = fSymbol;
	}

	@Column(name = "faccess_key")
	public String getFaccess_key() {
		return faccess_key;
	}

	public void setFaccess_key(String faccess_key) {
		this.faccess_key = faccess_key;
	}

	@Column(name = "fsecrt_key")
	public String getFsecrt_key() {
		return fsecrt_key;
	}

	public void setFsecrt_key(String fsecrt_key) {
		this.fsecrt_key = fsecrt_key;
	}

	@Column(name = "fip")
	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	@Column(name = "fport")
	public String getFport() {
		return fport;
	}

	public void setFport(String fport) {
		this.fport = fport;
	}

	@Transient
	public double getLastDealPrize() {
		return lastDealPrize;
	}

	public void setLastDealPrize(double lastDealPrize) {
		this.lastDealPrize = lastDealPrize;
	}

	@Transient
	public double getHigestBuyPrize() {
		return higestBuyPrize;
	}

	public void setHigestBuyPrize(double higestBuyPrize) {
		this.higestBuyPrize = higestBuyPrize;
	}

	@Transient
	public double getLowestSellPrize() {
		return lowestSellPrize;
	}

	public void setLowestSellPrize(double lowestSellPrize) {
		this.lowestSellPrize = lowestSellPrize;
	}

	@Transient
	public String getFid_s() {
		Integer id = this.getFid();
		if (id != null) {
			return String.valueOf(id);
		}
		return String.valueOf(0);
	}

	public void setFid_s(int fid_s) {
		this.fid_s = fid_s;
	}

	@Column(name = "FIsWithDraw")
	public boolean isFIsWithDraw() {
		return FIsWithDraw;
	}

	public void setFIsWithDraw(boolean fIsWithDraw) {
		FIsWithDraw = fIsWithDraw;
	}

	@Column(name = "furl")
	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	@Column(name = "fweburl")
	public String getFweburl() {
		return fweburl;
	}

	public void setFweburl(String fweburl) {
		this.fweburl = fweburl;
	}

	@Column(name = "ftype")
	public int getFtype() {
		return ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fisauto")
	public boolean isFisauto() {
		return fisauto;
	}

	public void setFisauto(boolean fisauto) {
		this.fisauto = fisauto;
	}

	@Column(name = "fisrecharge")
	public boolean isFisrecharge() {
		return fisrecharge;
	}

	public void setFisrecharge(boolean fisrecharge) {
		this.fisrecharge = fisrecharge;
	}

	@Transient
	public String getFtype_s() {
		return CoinTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public boolean isCanLend() {
		return true;
	}

	public void setCanLend(boolean canLend) {
		this.canLend = canLend;
	}

	@Column(name = "fmaxqty")
	public Double getFmaxqty() {
		return fmaxqty;
	}

	public void setFmaxqty(Double fmaxqty) {
		this.fmaxqty = fmaxqty;
	}

	@Column(name = "fminqty")
	public Double getFminqty() {
		return fminqty;
	}

	public void setFminqty(Double fminqty) {
		this.fminqty = fminqty;
	}

	@Transient
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

	@Column(name="mainAddr")
	public String getMainAddr() {
		return mainAddr;
	}

	public void setMainAddr(String mainAddr) {
		this.mainAddr = mainAddr;
	}
	@Column(name="isEth")
	public boolean isFisEth() {
		return fisEth;
	}

	public void setFisEth(boolean fisEth) {
		this.fisEth = fisEth;
	}

	@Column(name="startBlockId")
	public int getStartBlockId() {
		return startBlockId;
	}

	public void setStartBlockId(int startBlockId) {
		this.startBlockId = startBlockId;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="salt")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name="is_split_password")
	public Boolean getIsSplitPassword() {
		return isSplitPassword;
	}


	public void setIsSplitPassword(Boolean isSplitPassword) {
		this.isSplitPassword = isSplitPassword;
	}

	@Column(name="furl2")
	public String getFurl2() {
		return furl2;
	}

	public void setFurl2(String furl2) {
		this.furl2 = furl2;
	}


	@Column(name="auto_day_count")
	public Integer getAuto_day_count() {
		return auto_day_count;
	}

	public void setAuto_day_count(Integer auto_day_count) {
		this.auto_day_count = auto_day_count;
	}

	@Column(name="auto_single_limit")
	public BigDecimal getAuto_single_limit() {
		return auto_single_limit;
	}

	public void setAuto_single_limit(BigDecimal auto_single_limit) {
		this.auto_single_limit = auto_single_limit;
	}

	@Column(name="auto_day_limit")
	public BigDecimal getAuto_day_limit() {
		return auto_day_limit;
	}

	public void setAuto_day_limit(BigDecimal auto_day_limit) {
		this.auto_day_limit = auto_day_limit;
	}

	@Column(name="auto_password")
	public String getAuto_password() {
		return auto_password;
	}

	public void setAuto_password(String auto_password) {
		this.auto_password = auto_password;
	}
}