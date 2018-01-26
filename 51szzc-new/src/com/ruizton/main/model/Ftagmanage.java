package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-3-6.
 */
@Entity
@Table(name = "ftagmanage")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftagmanage implements Serializable{

    private int fid;

    private String fname;
    private Timestamp flastupdatetime;
    private Fadmin fupdateuser;
    private Fadmin fcreateuser;
    private Timestamp fcreatetime;
    private String furl;

    public Ftagmanage() {
    }

    public Ftagmanage(int fid, String fname, Timestamp flastupdatetime, Fadmin fupdateuser, Fadmin fcreateuser, Timestamp fcreatetime, String furl) {
        this.fid = fid;
        this.fname = fname;
        this.flastupdatetime = flastupdatetime;
        this.fupdateuser = fupdateuser;
        this.fcreateuser = fcreateuser;
        this.fcreatetime = fcreatetime;
        this.furl = furl;
    }

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid", unique = true, nullable = false)
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
    @Column(name = "fname")
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    @Column(name = "flastupdatetime")
    public Timestamp getFlastupdatetime() {
        return flastupdatetime;
    }

    public void setFlastupdatetime(Timestamp flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    @ManyToOne
    @JoinColumn(name = "fupdateuser")
    public Fadmin getFupdateuser() {
        return fupdateuser;
    }

    public void setFupdateuser(Fadmin fupdateuser) {
        this.fupdateuser = fupdateuser;
    }

    @ManyToOne
    @JoinColumn(name = "fcreateuser")
    public Fadmin getFcreateuser() {
        return fcreateuser;
    }

    public void setFcreateuser(Fadmin fcreateuser) {
        this.fcreateuser = fcreateuser;
    }
    @Column(name = "fcreatetime")
    public Timestamp getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Timestamp fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    @Column(name = "furl")
    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }
}
