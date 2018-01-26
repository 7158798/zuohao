package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zygong on 17-3-7.
 */
@Entity
@Table(name = "ftag")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ftag  implements Serializable {

    private int fid;

    private String fname;

    private Ftagmanage ftagmanageid;

    public Ftag() {
    }

    public Ftag(int fid, String fname, Ftagmanage ftagmanageid) {
        this.fid = fid;
        this.fname = fname;
        this.ftagmanageid = ftagmanageid;
    }

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
    @Column(name = "fname")
    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @ManyToOne
    @JoinColumn(name = "ftagmanageid")
    public Ftagmanage getFtagmanageid() {
        return ftagmanageid;
    }

    public void setFtagmanageid(Ftagmanage ftagmanageid) {
        this.ftagmanageid = ftagmanageid;
    }
}
