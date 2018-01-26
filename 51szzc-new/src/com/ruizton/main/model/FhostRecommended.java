package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-3-6.
 */
@Entity
@Table(name = "fhostrecommended")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FhostRecommended {

    private int fid;
    private String fName;
    private int fNumber;
    private Fadmin fCreateUser;
    private Timestamp fCreateTime;
    private Fadmin fUpdateUser;
    private Timestamp fUpdateTime;

    public FhostRecommended() {
    }

    public FhostRecommended(int fid, String fName, int fNumber, Fadmin fCreateUser, Timestamp fCreateTime, Fadmin fUpdateUser, Timestamp fUpdateTime) {
        this.fid = fid;
        this.fName = fName;
        this.fNumber = fNumber;
        this.fCreateUser = fCreateUser;
        this.fCreateTime = fCreateTime;
        this.fUpdateUser = fUpdateUser;
        this.fUpdateTime = fUpdateTime;
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
    @Column(name = "fName")
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }
    @Column(name = "fNumber")
    public int getfNumber() {
        return fNumber;
    }

    public void setfNumber(int fNumber) {
        this.fNumber = fNumber;
    }
    @OneToOne
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
    @OneToOne
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
