package com.ruizton.main.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by luwei on 17-3-15.
 */
@Entity
@Table(name = "falipay_bind")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FalipayBind {

    //流水号
    private Integer fid;

    //绑定用户
    private Fuser fuser;

    //支付宝帐号名称
    private String fname;

    //支付宝帐号
    private String faccount;

    //创建时间
    private Timestamp fcreateTime;

    //状态  1正常 2禁用
    private Integer fstatus;

    //版本号
    private Integer version;


    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fId",unique = true, nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FUs_fId")
    @JsonIgnore
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @Column( name="fName" )
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Column( name="fAccount" )
    public String getFaccount() {
        return faccount;
    }

    public void setFaccount(String faccount) {
        this.faccount = faccount;
    }

    @Column( name="fCreateTime" )
    public Timestamp getFcreateTime() {
        return fcreateTime;
    }

    public void setFcreateTime(Timestamp fcreateTime) {
        this.fcreateTime = fcreateTime;
    }

    @Column( name="fStatus" )
    public Integer getFstatus() {
        return fstatus;
    }

    public void setFstatus(Integer fstatus) {
        this.fstatus = fstatus;
    }

    @Column( name="version" )
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
