package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zygong on 17-2-28.
 */
@Entity
@Table(name = "fvideo")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fvideo implements Serializable {

    private int fid;
    private Fvideotype fvideotype;
    private String fTitle;
    private String fDescription;
    private String fPictureUrl;
    private Fadmin fCreateUser;
    private Timestamp fCreateTime;
    private Fadmin fUpdateUser;
    private Timestamp fUpdateTime;
    private String fVideoUrl;
    private int fPriority;

    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid",unique = true, nullable = false)
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fTypeId")
    public Fvideotype getFvideotype() {
        return fvideotype;
    }

    public void setFvideotype(Fvideotype fvideotype) {
        this.fvideotype = fvideotype;
    }

    @Column(name = "fTitle")
    public String getfTitle() {
        return fTitle;
    }

    public void setfTitle(String fTitle) {
        this.fTitle = fTitle;
    }


    @Column(name = "fDescription")
    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    @Column(name = "fPictureUrl")
    public String getfPictureUrl() {
        return fPictureUrl;
    }

    public void setfPictureUrl(String fPictureUrl) {
        this.fPictureUrl = fPictureUrl;
    }

    @OneToOne(fetch = FetchType.EAGER)
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


    @OneToOne(fetch = FetchType.EAGER)
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


    @Column(name = "fVideoUrl")
    public String getfVideoUrl() {
        return fVideoUrl;
    }

    public void setfVideoUrl(String fVideoUrl) {
        this.fVideoUrl = fVideoUrl;
    }

    @Column(name = "fPriority")
    public int getfPriority() {
        return fPriority;
    }

    public void setfPriority(int fPriority) {
        this.fPriority = fPriority;
    }
}
