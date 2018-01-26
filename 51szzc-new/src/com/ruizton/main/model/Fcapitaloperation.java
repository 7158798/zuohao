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

import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;

/**
 * Fcapitaloperation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fcapitaloperation")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fcapitaloperation implements java.io.Serializable {

	// Fields

	private int fid;
	private Systembankinfo systembankinfo;// 用户向这个账号汇款,只有充值用
	private Fuser fuser;
	private Timestamp fcreateTime;
	private Timestamp fLastUpdateTime;

	private double famount;
	private int ftype;// CapitalOperationTypeEnum，汇款or提现
	private String ftype_s;
	private int fstatus;// CapitalOperationInStatus||CapitalOperationOutStatus
	private String fstatus_s;
	
	private String fremittanceType;// RemittanceTypeEnum汇款方式
	
	private String fremark;// 用不着  支付宝支付时，记录支付宝返回的信息，页面不显示，以备后续查证

	private String fBank;
	private String fAccount;
	private String faccount_s;
	private String fPayee;// 汇款人or收款人
	private String fPhone;// 汇款人手机账号
	private Fadmin fAuditee_id ;
	private double ffees ;//手续费
	private int version ;
	private boolean fischarge;
	private String faddress;
	private String cancelReason;

	private String fstatus_f; //前端显示状态
	
	/** default constructor */
	public Fcapitaloperation() {
	}

     
	/** full constructor */
	public Fcapitaloperation(Fbankinfo fbankinfo,
			Systembankinfo systembankinfo, Fuser fuser, Timestamp fcreateTime,
			double famount, int ftype, int fstatus,
			String fremittanceType, String fremark) {
		this.systembankinfo = systembankinfo;
		this.fuser = fuser;
		this.fcreateTime = fcreateTime;
		this.famount = famount;
		this.ftype = ftype;
		this.fstatus = fstatus;
		this.fremittanceType = fremittanceType;
		this.fremark = fremark;
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
	@JoinColumn(name = "Sys_fId")
	public Systembankinfo getSystembankinfo() {
		return this.systembankinfo;
	}

	public void setSystembankinfo(Systembankinfo systembankinfo) {
		this.systembankinfo = systembankinfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUs_fId")
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
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

	@Column(name = "fRemittanceType")
	public String getFremittanceType() {
		return this.fremittanceType;
	}

	public void setFremittanceType(String fremittanceType) {
		this.fremittanceType = fremittanceType;
	}

	@Column(name = "fRemark", length = 256)
	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	@Transient
	public String getFstatus_s() {
		int status = this.getFstatus();
		if (this.getFtype() == CapitalOperationTypeEnum.RMB_IN) {
			return CapitalOperationInStatus.getEnumString(status);
		} else {
			return CapitalOperationOutStatus.getEnumString(status);
		}
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}

	@Transient
	public String getFstatus_f() {
		int status = this.getFstatus();
		if (this.getFtype() == CapitalOperationTypeEnum.RMB_IN) {
			return CapitalOperationInStatus.getFrontString(status);
		} else {
			return CapitalOperationOutStatus.getFrontString(status);
		}
	}

	public void setFstatus_f(String fstatus_f) {
		this.fstatus_f = fstatus_f;
	}

	@Column(name = "fBank", length = 256)
	public String getfBank() {
		return fBank;
	}

	public void setfBank(String fBank) {
		this.fBank = fBank;
	}

	@Column(name = "fAccount", length = 256)
	public String getfAccount() {
		return fAccount;
	}

	public void setfAccount(String fAccount) {
		this.fAccount = fAccount;
	}

	@Column(name = "fPayee", length = 256)
	public String getfPayee() {
		return fPayee;
	}

	public void setfPayee(String fPayee) {
		this.fPayee = fPayee;
	}

	@Column(name = "fPhone", length = 256)
	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	@Transient
	public String getFtype_s() {
		return CapitalOperationTypeEnum.getEnumString(this.getFtype());
	}

	public void setFtype_s(String ftype_s) {
		this.ftype_s = ftype_s;
	}

	@Column( name="fLastUpdateTime" )
	public Timestamp getfLastUpdateTime() {
		return fLastUpdateTime;
	}


	public void setfLastUpdateTime(Timestamp fLastUpdateTime) {
		this.fLastUpdateTime = fLastUpdateTime;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fAuditee_id")
	public Fadmin getfAuditee_id() {
		return fAuditee_id;
	}


	public void setfAuditee_id(Fadmin fAuditee_id) {
		this.fAuditee_id = fAuditee_id;
	}


	@Column(name="ffees")
	public double getFfees() {
		return ffees;
	}


	public void setFfees(double ffees) {
		this.ffees = ffees;
	}
	
	@Column(name="fischarge")
	public boolean isFischarge() {
		return fischarge;
	}

	public void setFischarge(boolean fischarge) {
		this.fischarge = fischarge;
	}
	
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Column(name = "faddress")
	public String getFaddress() {
		return faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	@Transient
	public String getFaccount_s() {
		String account = this.getfAccount();
		if(account == null || account.trim().length() == 0) return "";
		return account.replaceAll(" ", "");
	}

	public void setFaccount_s(String faccount_s) {
		this.faccount_s = faccount_s;
	}


	@Column(name="cancelReason")
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
