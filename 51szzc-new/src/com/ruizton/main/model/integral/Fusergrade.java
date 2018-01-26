package com.ruizton.main.model.integral;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by fenggq on 17-2-22.
 */
@Entity
@Table(name = "fusergrade")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fusergrade {
    private int fid;
    private String ftitle;
    private Integer fneedintegral;
    private BigDecimal foutfee;
    private BigDecimal fcapitalinfee;
    private BigDecimal virtualinfee;
    private BigDecimal tradefee;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid")
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "ftitle")
    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    @Basic
    @Column(name = "fneedintegral")
    public Integer getFneedintegral() {
        return fneedintegral;
    }

    public void setFneedintegral(Integer fneedintegral) {
        this.fneedintegral = fneedintegral;
    }


    @Basic
    @Column(name = "foutfee")
    public BigDecimal getFoutfee() {
        return foutfee;
    }

    public void setFoutfee(BigDecimal foutfee) {
        this.foutfee = foutfee;
    }

    @Basic
    @Column(name = "fcapitalinfee")
    public BigDecimal getFcapitalinfee() {
        return fcapitalinfee;
    }

    public void setFcapitalinfee(BigDecimal fcapitalinfee) {
        this.fcapitalinfee = fcapitalinfee;
    }

    @Basic
    @Column(name = "virtualinfee")
    public BigDecimal getVirtualinfee() {
        return virtualinfee;
    }

    public void setVirtualinfee(BigDecimal virtualinfee) {
        this.virtualinfee = virtualinfee;
    }

    @Basic
    @Column(name = "tradefee")
    public BigDecimal getTradefee() {
        return tradefee;
    }

    public void setTradefee(BigDecimal tradefee) {
        this.tradefee = tradefee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fusergrade that = (Fusergrade) o;

        if (fid != that.fid) return false;
        if (ftitle != null ? !ftitle.equals(that.ftitle) : that.ftitle != null) return false;
        if (fneedintegral != null ? !fneedintegral.equals(that.fneedintegral) : that.fneedintegral != null)
            return false;
        if (foutfee != null ? !foutfee.equals(that.foutfee) : that.foutfee != null) return false;
        if (fcapitalinfee != null ? !fcapitalinfee.equals(that.fcapitalinfee) : that.fcapitalinfee != null)
            return false;
        if (virtualinfee != null ? !virtualinfee.equals(that.virtualinfee) : that.virtualinfee != null) return false;
        if (tradefee != null ? !tradefee.equals(that.tradefee) : that.tradefee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fid;
        result = 31 * result + (ftitle != null ? ftitle.hashCode() : 0);
        result = 31 * result + (fneedintegral != null ? fneedintegral.hashCode() : 0);
        result = 31 * result + (foutfee != null ? foutfee.hashCode() : 0);
        result = 31 * result + (fcapitalinfee != null ? fcapitalinfee.hashCode() : 0);
        result = 31 * result + (virtualinfee != null ? virtualinfee.hashCode() : 0);
        result = 31 * result + (tradefee != null ? tradefee.hashCode() : 0);
        return result;
    }
}
