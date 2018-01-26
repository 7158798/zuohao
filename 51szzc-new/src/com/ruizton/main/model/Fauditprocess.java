package com.ruizton.main.model;

import com.ruizton.main.Enum.AuditProcessEnum;
import com.ruizton.main.Enum.CapitalOperationInStatus;
import com.ruizton.main.Enum.CapitalOperationOutStatus;
import com.ruizton.main.Enum.CapitalOperationTypeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by a123 on 17-3-14.
 */
@Entity
@Table(name = "fauditprocess")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fauditprocess {
    private int fId;
    private Integer ftype;
    private Integer fIsneedPwd;
    private Timestamp fLastUpdateTime;

    private Frole frole1;
    private Frole frole2;
    private Frole frole3;

    private Fadmin fmodify_id;

    private String ftype_s;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fId", unique = true, nullable = false)
    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    @Basic
    @Column(name = "ftype")
    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    @Basic
    @Column(name = "fIsneedPwd")
    public Integer getfIsneedPwd() {
        return fIsneedPwd;
    }

    public void setfIsneedPwd(Integer fIsneedPwd) {
        this.fIsneedPwd = fIsneedPwd;
    }

    @Basic
    @Column(name = "fLastUpdateTime")
    public Timestamp getfLastUpdateTime() {
        return fLastUpdateTime;
    }

    public void setfLastUpdateTime(Timestamp fLastUpdateTime) {
        this.fLastUpdateTime = fLastUpdateTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frole_fId1")
    public Frole getFrole1() {
        return frole1;
    }

    public void setFrole1(Frole frole1) {
        this.frole1 = frole1;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frole_fId2")
    public Frole getFrole2() {
        return frole2;
    }

    public void setFrole2(Frole frole2) {
        this.frole2 = frole2;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frole_fId3")
    public Frole getFrole3() {
        return frole3;
    }


    public void setFrole3(Frole frole3) {
        this.frole3 = frole3;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fmodify_id")
    public Fadmin getFmodify_id() {
        return fmodify_id;
    }

    public void setFmodify_id(Fadmin fmodify_id) {
        this.fmodify_id = fmodify_id;
    }


    @Transient
    public String getFtype_s() {
        return AuditProcessEnum.getMap().get(this.getFtype());
    }

    public void setFtype_s(String ftype_s) {
        this.ftype_s = ftype_s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fauditprocess that = (Fauditprocess) o;

        if (fId != that.fId) return false;
        if (ftype != null ? !ftype.equals(that.ftype) : that.ftype != null) return false;
        if (fIsneedPwd != null ? !fIsneedPwd.equals(that.fIsneedPwd) : that.fIsneedPwd != null) return false;
        if (fLastUpdateTime != null ? !fLastUpdateTime.equals(that.fLastUpdateTime) : that.fLastUpdateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fId;
        result = 31 * result + (ftype != null ? ftype.hashCode() : 0);
        result = 31 * result + (fIsneedPwd != null ? fIsneedPwd.hashCode() : 0);
        result = 31 * result + (fLastUpdateTime != null ? fLastUpdateTime.hashCode() : 0);
        return result;
    }
}
