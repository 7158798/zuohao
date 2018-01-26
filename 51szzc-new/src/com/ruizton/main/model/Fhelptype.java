package com.ruizton.main.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zygong on 17-3-16.
 */
@Entity
@Table(name = "fhelptype")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fhelptype implements java.io.Serializable {

    private int fid;
    private String fname;
    private String fdescription;
    private boolean fisding;
    private Timestamp fcreatetime;
    private Fadmin fcreateuser;
    private Timestamp fupdatetime;
    private Fadmin fupdateuser;
    private int forder;
    private List<Fhelp> fhelp_index;

    public Fhelptype() {
    }

    public Fhelptype(int fid, String fname, String fdescription, boolean fisding, Timestamp fcreatetime, Fadmin fcreateuser, Timestamp fupdatetime, Fadmin fupdateuser, int forder) {
        this.fid = fid;
        this.fname = fname;
        this.fdescription = fdescription;
        this.fisding = fisding;
        this.fcreatetime = fcreatetime;
        this.fcreateuser = fcreateuser;
        this.fupdatetime = fupdatetime;
        this.fupdateuser = fupdateuser;
        this.forder = forder;
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

    @Column(name = "fdescription")
    public String getFdescription() {
        return fdescription;
    }

    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
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

    @Column(name = "forder")
    public int getForder() {
        return forder;
    }

    public void setForder(int forder) {
        this.forder = forder;
    }

    @Transient
    public List<Fhelp> getFhelp_index() {
        return fhelp_index;
    }

    public void setFhelp_index(List<Fhelp> fhelp_index) {
        this.fhelp_index = fhelp_index;
    }
}
