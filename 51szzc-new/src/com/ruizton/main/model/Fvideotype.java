package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 视频类型
 * Created by zygong on 17-2-28.
 */
@Entity
@Table(name = "fvideotype")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvideotype implements Serializable {

    private int fid;
    private String fName;
    private String fDescription;
    private Fadmin fCreateUser;
    private Timestamp fCreateTime;
    private Fadmin fUpdateUser;
    private Timestamp fUpdateTime;


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


    @Column(name = "fName")
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }


    @Column(name = "fDescription")
    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fCreateUser")
    public Fadmin getfCreateUser() {
        return fCreateUser;
    }

    public void setfCreateUser(Fadmin fCreateUser) {
        this.fCreateUser = fCreateUser;
    }


    @Column(name = "fCreateTime")
    public Timestamp getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Timestamp fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fUpdateUser")
    public Fadmin getfUpdateUser() {
        return fUpdateUser;
    }

    public void setfUpdateUser(Fadmin fUpdateUser) {
        this.fUpdateUser = fUpdateUser;
    }


    @Column(name = "fUpdateTime")
    public Timestamp getfUpdateTime() {
        return fUpdateTime;
    }

    public void setfUpdateTime(Timestamp fUpdateTime) {
        this.fUpdateTime = fUpdateTime;
    }

   }
