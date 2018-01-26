package com.ruizton.main.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ruizton.main.Enum.VirtualCapitalOperationInStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationOutStatusEnum;
import com.ruizton.main.Enum.VirtualCapitalOperationTypeEnum;

/**
 * Fvirtualcaptualoperation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fvirtualcaptualoperation")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvirtualcaptualoperation implements java.io.Serializable {

	// Fields
	private int fid;
	private Fuser fuser;
	private Fvirtualcointype fvirtualcointype;
	private Timestamp fcreateTime;
	private Timestamp flastUpdateTime;
	private double famount;
	private double ffees;
	private int ftype;// 操作类型 VirtualCapitalOperationTypeEnum
	private String ftype_s;
	private int fstatus;// VirtualCoinOperationInStatusEnum||VirtualCoinOperationOutStatusEnum
	private String fstatus_s;
	private String withdraw_virtual_address;// 提现地址
	private String recharge_virtual_address;// 充值地址
	private String ftradeUniqueNumber ;
	private int fconfirmations ;//纭鏁�
	private int version;
	private boolean fhasOwner ;//鍏呭�艰褰曟槸鍚﹀綊灞炴煇鐢ㄦ埛
    private String fischarge;
    //审核拒绝原因
	private String cancelReason;
	// 合同充值
	private Boolean isContract;

	//邮件链接确认时间
	private Timestamp mailLinkVerDate;

	//邮件链接UUID
	private String  mailLinkUUID;


	private String fstatus_f; //前端显示状态
	// Constructors

	/** default constructor */
	public Fvirtualcaptualoperation() {
	}

	/** full constructor */
	public Fvirtualcaptualoperation(Fvirtualaddress fvirtualaddress,
			Fuser fuser, Fvirtualcointype fvirtualcointype, int fusFId,
			Timestamp fcreateTime, double famount, int ftype,
			int fstatus, double ffees) {
		this.fuser = fuser;
		this.fvirtualcointype = fvirtualcointype;
		this.fcreateTime = fcreateTime;
		this.famount = famount;
		this.ftype = ftype;
		this.fstatus = fstatus;
		this.ffees = ffees;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUs_fId2")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fVi_fId2")
	public Fvirtualcointype getFvirtualcointype() {
		return this.fvirtualcointype;
	}

	public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
		this.fvirtualcointype = fvirtualcointype;
	}


	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fAmount", precision = 12, scale = 0)
	public double getFamount() {
		return this.famount;
	}

	public void setFamount(double famount) {
		this.famount = famount;
	}

	@Column(name = "fType")
	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "flastUpdateTime")
	public Timestamp getFlastUpdateTime() {
		return flastUpdateTime;
	}

	public void setFlastUpdateTime(Timestamp flastUpdateTime) {
		this.flastUpdateTime = flastUpdateTime;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Transient
	public String getFtype_s() {
		return VirtualCapitalOperationTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		if (this.getFtype() == VirtualCapitalOperationTypeEnum.COIN_IN) {
			return VirtualCapitalOperationInStatusEnum.getEnumString(status);
		} else {
			return VirtualCapitalOperationOutStatusEnum.getEnumString(status);
		}
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Column(name = "withdraw_virtual_address")
	public String getWithdraw_virtual_address() {
		return withdraw_virtual_address;
	}

	public void setWithdraw_virtual_address(String withdraw_virtual_address) {
		this.withdraw_virtual_address = withdraw_virtual_address;
	}

	@Column(name = "recharge_virtual_address")
	public String getRecharge_virtual_address() {
		return recharge_virtual_address;
	}

	public void setRecharge_virtual_address(String recharge_virtual_address) {
		this.recharge_virtual_address = recharge_virtual_address;
	}

	@Column(name = "ffees")
	public double getFfees() {
		return ffees;
	}

	public void setFfees(double ffees) {
		this.ffees = ffees;
	}

	@Column(name="ftradeUniqueNumber")
	public String getFtradeUniqueNumber() {
		return ftradeUniqueNumber;
	}

	public void setFtradeUniqueNumber(String ftradeUniqueNumber) {
		this.ftradeUniqueNumber = ftradeUniqueNumber;
	}

	@Column(name="fconfirmations")
	public int getFconfirmations() {
		return fconfirmations;
	}

	public void setFconfirmations(int fconfirmations) {
		this.fconfirmations = fconfirmations;
	}

	@Column(name="fhasOwner")
	public boolean isFhasOwner() {
		return fhasOwner;
	}

	public void setFhasOwner(boolean fhasOwner) {
		this.fhasOwner = fhasOwner;
	}
	
	@Column(name="fischarge")
	public String getFischarge() {
		return fischarge;
	}

	public void setFischarge(String fischarge) {
		this.fischarge = fischarge;
	}

	@Column(name="cancelReason")
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name="is_contract")
	public Boolean getIsContract() {
		return isContract;
	}

	public void setIsContract(Boolean isContract) {
		this.isContract = isContract;
	}

	@Column(name="mailLinkVerDate")
	public Timestamp getMailLinkVerDate() {
		return mailLinkVerDate;
	}

	public void setMailLinkVerDate(Timestamp mailLinkVerDate) {
		this.mailLinkVerDate = mailLinkVerDate;
	}

	@Column(name="mailLinkUUID")
	public String getMailLinkUUID() {
		return mailLinkUUID;
	}

	public void setMailLinkUUID(String mailLinkUUID) {
		this.mailLinkUUID = mailLinkUUID;
	}


	@Transient
	public String getFstatus_f() {
		int status = this.getFstatus();
		if (this.getFtype() == VirtualCapitalOperationTypeEnum.COIN_IN) {
			return VirtualCapitalOperationInStatusEnum.getFrontString(status);
		} else {
			return VirtualCapitalOperationOutStatusEnum.getFrontString(status);
		}
	}

	public void setFstatus_f(String fstatus_f) {
		this.fstatus_f = fstatus_f;
	}
}