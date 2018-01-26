package com.ruizton.main.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-3-16.
 */
@Entity
@Table(name = "fhelp")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fhelp implements java.io.Serializable {

    private int fid;
    private String ftitle;
    private Fhelptype fhelptype;
    private boolean fisding;
    private Timestamp fcreatetime;
    private Fadmin fcreateuser;
    private Timestamp fupdatetime;
    private Fadmin fupdateuser;
    private String fcontent;

    public int getForder() {
        return forder;
    }

    public void setForder(int forder) {
        this.forder = forder;
    }

    private int forder;

    public Fhelp() {
    }

    public Fhelp(int fid, String ftitle, Fhelptype fhelptype, boolean fisding, Timestamp fcreatetime, Fadmin fcreateuser, Timestamp fupdatetime, Fadmin fupdateuser, String fcontent) {
        this.fid = fid;
        this.ftitle = ftitle;
        this.fhelptype = fhelptype;
        this.fisding = fisding;
        this.fcreatetime = fcreatetime;
        this.fcreateuser = fcreateuser;
        this.fupdatetime = fupdatetime;
        this.fupdateuser = fupdateuser;
        this.fcontent = fcontent;
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

    @Column(name = "ftitle")
    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String fname) {
        this.ftitle = fname;
    }

    @OneToOne
    @JoinColumn(name = "fhelptype")
    public Fhelptype getFhelptype() {
        return fhelptype;
    }

    public void setFhelptype(Fhelptype fhelptype) {
        this.fhelptype = fhelptype;
    }

    @Column(name = "fisding")
    public boolean isFisding() {
        return fisding;
    }

    public void setFisding(boolean fisding) {
        this.fisding = fisding;
    }

    @Column(name = "fcreatetime")
    public Timestamp getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Timestamp fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    @OneToOne
    @JoinColumn(name = "fcreateuser")
    public Fadmin getFcreateuser() {
        return fcreateuser;
    }

    public void setFcreateuser(Fadmin fcreateuser) {
        this.fcreateuser = fcreateuser;
    }

    @Column(name = "fupdatetime")
    public Timestamp getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Timestamp fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    @OneToOne
    @JoinColumn(name = "fupdateuser")
    public Fadmin getFupdateuser() {
        return fupdateuser;
    }

    public void setFupdateuser(Fadmin fupdateuser) {
        this.fupdateuser = fupdateuser;
    }

    @Column(name = "fcontent")
    public String getFcontent() {
        return fcontent;
    }

    public void setFcontent(String fcontent) {
        this.fcontent = fcontent;
    }
}
