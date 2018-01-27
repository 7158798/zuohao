package com.szzc.facade.fquotes.pojo.po;

import java.io.Serializable;
import java.util.Date;

public class Fquotes implements Serializable {

    /**
     * 对应表字段为：fquotes.fid
     */
    private Integer fid;

    /**
     * 平台，对应表字段为：fquotes.fplatform
     */
    private String fplatform;

    /**
     * 人民币，对应表字段为：fquotes.frmb
     */
    private String frmb;

    /**
     * 美元，对应表字段为：fquotes.fdollar
     */
    private String fdollar;

    /**
     * 比率，对应表字段为：fquotes.frate
     */
    private String frate;

    /**
     * 1:up   2:down，对应表字段为：fquotes.fupordown
     */
    private String fupordown;

    /**
     * 对应表字段为：fquotes.fcreatetime
     */
    private Date fcreatetime;

    /**
     * 币种id，对应表字段为：fquotes.fvirtualcointypeid
     */
    private Integer fvirtualcointypeid;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFplatform() {
        return fplatform;
    }

    public void setFplatform(String fplatform) {
        this.fplatform = fplatform == null ? null : fplatform.trim();
    }

    public String getFrmb() {
        return frmb;
    }

    public void setFrmb(String frmb) {
        this.frmb = frmb == null ? null : frmb.trim();
    }

    public String getFdollar() {
        return fdollar;
    }

    public void setFdollar(String fdollar) {
        this.fdollar = fdollar == null ? null : fdollar.trim();
    }

    public String getFrate() {
        return frate;
    }

    public void setFrate(String frate) {
        this.frate = frate == null ? null : frate.trim();
    }

    public String getFupordown() {
        return fupordown;
    }

    public void setFupordown(String fupordown) {
        this.fupordown = fupordown == null ? null : fupordown.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public Integer getFvirtualcointypeid() {
        return fvirtualcointypeid;
    }

    public void setFvirtualcointypeid(Integer fvirtualcointypeid) {
        this.fvirtualcointypeid = fvirtualcointypeid;
    }
}