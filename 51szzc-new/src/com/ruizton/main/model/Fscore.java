package com.ruizton.main.model;

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
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Fscore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "fscore")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Fscore implements java.io.Serializable {

	// Fields

	private int fid;
	private double fscore;
	private int flevel;
	private Set<Fuser> fusers = new HashSet<Fuser>(0);
	private int version;
	private int fgroupqty;
	private int ftreeqty;
	private int fkillQty;
	private boolean fissend;
    private boolean fissign;
    private boolean fisopen;

	private boolean integralPhone;
	private boolean integralEmail;
	private boolean integralRealName;
	private boolean integralBankInfo;
	private boolean integralTradePWD;
	private boolean integralGA;
	private boolean integralCapitalIn;
	private boolean integralFirstTrade;
	// Constructors

	@Column(name = "integral_phone")
	public boolean isIntegralPhone() {
		return integralPhone;
	}

	public void setIntegralPhone(boolean integralPhone) {
		this.integralPhone = integralPhone;
	}

	@Column(name = "integral_email")
	public boolean isIntegralEmail() {
		return integralEmail;
	}

	public void setIntegralEmail(boolean integralEmail) {
		this.integralEmail = integralEmail;
	}

	@Column(name = "integral_realName")
	public boolean isIntegralRealName() {
		return integralRealName;
	}

	public void setIntegralRealName(boolean integralRealName) {
		this.integralRealName = integralRealName;
	}

	@Column(name = "integral_bankInfo")
	public boolean isIntegralBankInfo() {
		return integralBankInfo;
	}

	public void setIntegralBankInfo(boolean integralBankInfo) {
		this.integralBankInfo = integralBankInfo;
	}

	@Column(name = "integral_tradePWD")
	public boolean isIntegralTradePWD() {
		return integralTradePWD;
	}

	public void setIntegralTradePWD(boolean integralTradePWD) {
		this.integralTradePWD = integralTradePWD;
	}

	@Column(name = "integral_GA")
	public boolean isIntegralGA() {
		return integralGA;
	}

	public void setIntegralGA(boolean integralGA) {
		this.integralGA = integralGA;
	}

	@Column(name = "integral_capitalIn")
	public boolean isIntegralCapitalIn() {
		return integralCapitalIn;
	}

	public void setIntegralCapitalIn(boolean integralCapitalIn) {
		this.integralCapitalIn = integralCapitalIn;
	}

	@Column(name = "integral_firstTrade")
	public boolean isIntegralFirstTrade() {
		return integralFirstTrade;
	}

	public void setIntegralFirstTrade(boolean integralFirstTrade) {
		this.integralFirstTrade = integralFirstTrade;
	}

	/** default constructor */
	public Fscore() {
	}

	/** full constructor */
	public Fscore(int fscore, int flevel, Set<Fuser> fusers) {
		this.fscore = fscore;
		this.flevel = flevel;
		this.fusers = fusers;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@Column(name = "fscore")
	public double getFscore() {
		return this.fscore;
	}

	public void setFscore(double fscore) {
		this.fscore = fscore;
	}

	@Column(name = "flevel")
	public int getFlevel() {
		return this.flevel;
	}

	public void setFlevel(int flevel) {
		this.flevel = flevel;
	}
	
	@Column(name = "fgroupqty")
    public int getFgroupqty() {
		return fgroupqty;
	}


	public void setFgroupqty(int fgroupqty) {
		this.fgroupqty = fgroupqty;
	}

	@Column(name = "ftreeqty")
	public int getFtreeqty() {
		return ftreeqty;
	}


	public void setFtreeqty(int ftreeqty) {
		this.ftreeqty = ftreeqty;
	}

	@Column(name = "fkillQty")
	public int getFkillQty() {
		return fkillQty;
	}

	public void setFkillQty(int fkillQty) {
		this.fkillQty = fkillQty;
	}
	
	@Column(name = "fissend")
	public boolean isFissend() {
		return fissend;
	}

	public void setFissend(boolean fissend) {
		this.fissend = fissend;
	}
	
	@Column(name = "fissign")
	public boolean isFissign() {
		return fissign;
	}

	public void setFissign(boolean fissign) {
		this.fissign = fissign;
	}
	
	@Column(name = "fisopen")
	public boolean isFisopen() {
		return fisopen;
	}

	public void setFisopen(boolean fisopen) {
		this.fisopen = fisopen;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fscore")
	public Set<Fuser> getFusers() {
		return this.fusers;
	}

	public void setFusers(Set<Fuser> fusers) {
		this.fusers = fusers;
	}

	@Version
	@Column(name = "version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}