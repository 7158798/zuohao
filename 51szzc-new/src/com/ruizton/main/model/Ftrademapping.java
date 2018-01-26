package com.ruizton.main.model;

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

import com.ruizton.main.Enum.TrademappingStatusEnum;

/**
 * Ftrademapping entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftrademapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftrademapping implements java.io.Serializable {

	// Fields

	private Integer fid;
	private Integer version;
	private Fvirtualcointype fvirtualcointypeByFvirtualcointype1;// 法币
	private Fvirtualcointype fvirtualcointypeByFvirtualcointype2;// 交易币
	private String ftradeTime;// 交易时间
	private boolean fisLimit;// 是否允许市价交易
	private int fstatus;//TrademappingStatusEnum
	private String fstatus_s;
	private int fcount1;// 单价小数位
	private int fcount2;// 数量小数位
	private double fminBuyCount;
	private double fminBuyPrice;
	private double fminBuyAmount;
	private double fprice;//开盘价

	// Constructors

	/** default constructor */
	public Ftrademapping() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "fid", unique = true, nullable = false)
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvirtualcointype2")
	public Fvirtualcointype getFvirtualcointypeByFvirtualcointype2() {
		return this.fvirtualcointypeByFvirtualcointype2;
	}

	public void setFvirtualcointypeByFvirtualcointype2(
			Fvirtualcointype fvirtualcointypeByFvirtualcointype2) {
		this.fvirtualcointypeByFvirtualcointype2 = fvirtualcointypeByFvirtualcointype2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fvirtualcointype1")
	public Fvirtualcointype getFvirtualcointypeByFvirtualcointype1() {
		return this.fvirtualcointypeByFvirtualcointype1;
	}

	public void setFvirtualcointypeByFvirtualcointype1(
			Fvirtualcointype fvirtualcointypeByFvirtualcointype1) {
		this.fvirtualcointypeByFvirtualcointype1 = fvirtualcointypeByFvirtualcointype1;
	}

	@Column(name = "ftradeTime")
	public String getFtradeTime() {
		return this.ftradeTime;
	}

	public void setFtradeTime(String ftradeTime) {
		this.ftradeTime = ftradeTime;
	}

	@Column(name = "fisLimit")
	public boolean isFisLimit() {
		return fisLimit;
	}

	public void setFisLimit(boolean fisLimit) {
		this.fisLimit = fisLimit;
	}

	@Column(name = "fstatus")
	public int getFstatus() {
		return fstatus;
	}

	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}

	@Column(name = "fcount1")
	public int getFcount1() {
		return fcount1;
	}

	public void setFcount1(int fcount1) {
		this.fcount1 = fcount1;
	}

	@Column(name = "fcount2")
	public int getFcount2() {
		return fcount2;
	}

	public void setFcount2(int fcount2) {
		this.fcount2 = fcount2;
	}

	@Column(name = "fminBuyCount")
	public double getFminBuyCount() {
		return fminBuyCount;
	}

	public void setFminBuyCount(double fminBuyCount) {
		this.fminBuyCount = fminBuyCount;
	}

	@Column(name = "fminBuyPrice")
	public double getFminBuyPrice() {
		return fminBuyPrice;
	}

	public void setFminBuyPrice(double fminBuyPrice) {
		this.fminBuyPrice = fminBuyPrice;
	}

	@Column(name = "fminBuyAmount")
	public double getFminBuyAmount() {
		return fminBuyAmount;
	}

	public void setFminBuyAmount(double fminBuyAmount) {
		this.fminBuyAmount = fminBuyAmount;
	}

	@Column(name = "fprice")
	public double getFprice() {
		return fprice;
	}

	public void setFprice(double fprice) {
		this.fprice = fprice;
	}
	
	@Transient
	public String getFstatus_s() {
		return TrademappingStatusEnum.getEnumString(this.getFstatus());
	}

	public void setFstatus_s(String fstatus_s) {
		this.fstatus_s = fstatus_s;
	}


}