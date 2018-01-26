package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by luwei on 17-3-28.
 */
@Entity
@Table(name = "fquestionvalidate")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fquestionvalidate {

    private Integer fid;

    private Fuser fuser;

    private String fquestion;

    private String fanswer;

    private Integer forder;

    private Timestamp fcreateTime;


    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "fid",unique = true, nullable = false)
    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fuid")
    public Fuser getFuser() {
        return fuser;
    }

    public void setFuser(Fuser fuser) {
        this.fuser = fuser;
    }

    @Column(name = "fquestion")
    public String getFquestion() {
        return fquestion;
    }

    public void setFquestion(String fquestion) {
        this.fquestion = fquestion;
    }

    @Column(name = "fanswer")
    public String getFanswer() {
        return fanswer;
    }

    public void setFanswer(String fanswer) {
        this.fanswer = fanswer;
    }

    @Column(name = "forder")
    public Integer getForder() {
        return forder;
    }

    public void setForder(Integer forder) {
        this.forder = forder;
    }

    @Column(name = "fcreateTime")
    public Timestamp getFcreateTime() {
        return fcreateTime;
    }

    public void setFcreateTime(Timestamp fcreateTime) {
        this.fcreateTime = fcreateTime;
    }
}
