package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 每天15点的，用户虚拟币，价格信息
 * Created by luwei on 17-3-6.
 */
@Entity
@Table(name = "ftimewallet")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftimewallet {

    //流水号
    private Integer fid;

    //虚拟币
    private Fvirtualcointype fvirtualcointype;

    //持有总量
    private BigDecimal ftotal;

    //价格
    private BigDecimal fprice;

    //用户
    private Fuser fuser;

    //创建时间
    private Timestamp fcreateTime;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fId", unique = true, nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fvi_fid")
    public Fvirtualcointype getFvirtualcointype() {
        return fvirtualcointype;
    }

    public void setFvirtualcointype(Fvirtualcointype fvirtualcointype) {
        this.fvirtualcointype = fvirtualcointype;
    }

    @Column(name = "ftotal")
    public BigDecimal getFtotal() {
        return ftotal;
    }

    public void setFtotal(BigDecimal ftotal) {
        this.ftotal = ftotal;
    }

    @Column(name = "fprice")
    public BigDecimal getFprice() {
        return fprice;
    }

    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuid")
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @Column(name = "fcreateTime")
    public Timestamp getFcreateTime() {
        return fcreateTime;
    }

    public void setFcreateTime(Timestamp fcreateTime) {
        this.fcreateTime = fcreateTime;
    }
}
