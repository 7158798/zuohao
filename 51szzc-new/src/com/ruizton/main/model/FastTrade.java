package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lx on 17-3-7.
 */
@Entity
@Table(name = "t_fast_trade")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FastTrade {

    private Integer id;

    private Ftrademapping ftrademapping;

    private Fuser user;

    private String randomNum;

    private String randomTime;

    private Timestamp createDate;

    private Timestamp modifiedDate;

    private int version;


    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_mapping_id")
    public Ftrademapping getFtrademapping() {
        return ftrademapping;
    }

    public void setFtrademapping(Ftrademapping ftrademapping) {
        this.ftrademapping = ftrademapping;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Fuser getUser() {
        return user;
    }

    public void setUser(Fuser user) {
        this.user = user;
    }

    @Column(name = "random_num")
    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    @Column(name = "random_time")
    public String getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(String randomTime) {
        this.randomTime = randomTime;
    }

    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "modified_date")
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Version
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
