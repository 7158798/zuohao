package com.ruizton.main.model.OtherPlatform;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-3-23.
 */
@Entity
@Table(name = "fquotes")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fquotes implements Serializable{
    private int fid;
    private String fplatform;
    private String frmb;
    private String fdollar;
    private String frate;
    private String fupordown;
    private Timestamp fcreatetime;
    private int fvirtualcointypeid;

    public Fquotes() {
    }

    public Fquotes(int fid, String fplatform, String frmb, String fdollar, String frate, String fupordown, Timestamp fcreatetime, int fvirtualcointypeid) {
        this.fid = fid;
        this.fplatform = fplatform;
        this.frmb = frmb;
        this.fdollar = fdollar;
        this.frate = frate;
        this.fupordown = fupordown;
        this.fcreatetime = fcreatetime;
        this.fvirtualcointypeid = fvirtualcointypeid;
    }

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fId", unique = true, nullable = false)
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    @Column(name = "fplatform")
    public String getFplatform() {
        return fplatform;
    }

    public void setFplatform(String fplatform) {
        this.fplatform = fplatform;
    }

    @Column(name = "frmb")
    public String getFrmb() {
        return frmb;
    }

    public void setFrmb(String frmb) {
        this.frmb = frmb;
    }

    @Column(name = "fdollar")
    public String getFdollar() {
        return fdollar;
    }

    public void setFdollar(String fdollar) {
        this.fdollar = fdollar;
    }

    @Column(name = "frate")
    public String getFrate() {
        return frate;
    }

    public void setFrate(String frate) {
        this.frate = frate;
    }

    @Column(name = "fupordown")
    public String getFupordown() {
        return fupordown;
    }

    public void setFupordown(String fupordown) {
        this.fupordown = fupordown;
    }

    @Column(name = "fcreatetime")
    public Timestamp getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Timestamp fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    @Column(name = "fvirtualcointypeid")
    public int getFvirtualcointypeid() {
        return fvirtualcointypeid;
    }

    public void setFvirtualcointypeid(int fvirtualcointypeid) {
        this.fvirtualcointypeid = fvirtualcointypeid;
    }
}
