package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Farticletype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "farticletype")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Farticletype implements java.io.Serializable {

	// Fields

	private int fid;
	private String fname;
	private String fkeywords;
	private String fdescription;
	private Timestamp flastCreateDate;
	private Timestamp flastModifyDate;
	private Set<Farticle> farticles = new HashSet<Farticle>(0);
	private int version ;
	private Fadmin fcreateUser;
	// Constructors

	/** default constructor */
	public Farticletype() {
	}

	/** full constructor */
	public Farticletype(String fname, String fkeywords, String fdescription,
			Timestamp flastCreateDate, Timestamp flastModifyDate,
			Set<Farticle> farticles, Fadmin fcreateUser) {
		this.fname = fname;
		this.fkeywords = fkeywords;               	
		this.fdescription = fdescription;
		this.flastCreateDate = flastCreateDate;
		this.flastModifyDate = flastModifyDate;
		this.farticles = farticles;
		this.fcreateUser = fcreateUser;
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

	@Column(name = "fName", length = 128)
	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "fKeywords", length = 1024)
	public String getFkeywords() {
		return this.fkeywords;
	}

	public void setFkeywords(String fkeywords) {
		this.fkeywords = fkeywords;
	}

	@Column(name = "fDescription", length = 1024)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "fLastCreateDate", length = 0)
	public Timestamp getFlastCreateDate() {
		return this.flastCreateDate;
	}

	public void setFlastCreateDate(Timestamp flastCreateDate) {
		this.flastCreateDate = flastCreateDate;
	}
	
	@ManyToOne
	@JoinColumn(name = "fcreateUser")
	public Fadmin getfCreateUser() {
		return fcreateUser;
	}

	public void setfCreateUser(Fadmin fcreateUser) {
		this.fcreateUser = fcreateUser;
	}

	@Column(name = "fLastModifyDate", length = 0)
	public Timestamp getFlastModifyDate() {
		return this.flastModifyDate;
	}

	public void setFlastModifyDate(Timestamp flastModifyDate) {
		this.flastModifyDate = flastModifyDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "farticletype")
	public Set<Farticle> getFarticles() {
		return this.farticles;
	}

	public void setFarticles(Set<Farticle> farticles) {
		this.farticles = farticles;
	}
	@Version
    @Column(name="version")
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}



}