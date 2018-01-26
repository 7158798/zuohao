package com.ruizton.main.model;

import java.sql.Timestamp;

import javax.persistence.*;

import com.ruizton.main.Enum.BankTypeEnum;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * FbankinfoWithdraw entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fbankinfo_withdraw")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FbankinfoWithdraw implements java.io.Serializable {

	// Fields

	private int fid;
	private int version;
	private Fuser fuser;
	private String fname;
	private String fbankNumber;
	private int fbankType;
	private Timestamp fcreateTime;
	private int fstatus;
	private String fothers;
	private String faddress;
	private String url;
	private String bgColor;
	// Constructors

	/** default constructor */
	public FbankinfoWithdraw() {
	}

	/** full constructor */
	public FbankinfoWithdraw(Fuser fuser, String fname, String fbankNumber,
			int fbankType, Timestamp fcreateTime, int fstatus) {
		this.fuser = fuser;
		this.fname = fname;
		this.fbankNumber = fbankNumber;
		this.fbankType = fbankType;
		this.fcreateTime = fcreateTime;
		this.fstatus = fstatus;
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

	@Version
	@Column(name = "version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUs_fId")
	@JsonIgnore
	public Fuser getFuser() {
		return this.fuser;
	}

	public void setFuser(Fuser fuser) {
		this.fuser = fuser;
	}

	@Column(name = "fName", length = 128)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fBankNumber", length = 128)
	public String getFbankNumber() {
		return this.fbankNumber;
	}

	public void setFbankNumber(String fbankNumber) {
		this.fbankNumber = fbankNumber;
	}

	@Column(name = "fBankType")
	public int getFbankType() {
		return this.fbankType;
	}

	public void setFbankType(int fbankType) {
		this.fbankType = fbankType;
	}

	@Column(name = "fCreateTime", length = 0)
	public Timestamp getFcreateTime() {
		return this.fcreateTime;
	}

	public void setFcreateTime(Timestamp fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	@Column(name = "fStatus")
	public int getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}
	
	@Column(name="faddress")
	public String getFaddress() {
		return faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}
	
	@Column(name="fothers")
	public String getFothers() {
		return fothers;
	}

	public void setFothers(String fothers) {
		this.fothers = fothers;
	}

	@Transient
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Transient
	public String getBgColor() {
		return BankTypeEnum.getBgColor(fbankType);
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
}