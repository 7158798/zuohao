package com.ruizton.main.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by lx on 17-1-24.
 */
@Entity
@Table(name = "t_auto_order")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutoOrder implements Serializable {
    private Integer id;
    private Ftrademapping ftrademapping;
    private Integer random;
    private BigDecimal ratio;
    private Integer cancelTime;
    private Boolean messageFlag;
    private String mobilePhone;
    private Fuser user;
    private Integer sleepTime;
    private Integer maxOrder;
    private BigDecimal allXnb;
    private BigDecimal allCny;
    private Timestamp createDate;
    private Timestamp modifiedDate;
    // 释放时间
    private Integer releaseTime;
    // 请求的数据源地址，多个数据源用逗号隔开
    private String urls;
    private Integer reqSleepTime;
    // 备选用户
    private Fuser reserveUser;
    // 冻结虚拟币
    private BigDecimal xnbFrozen;
    // 冻结人民币
    private BigDecimal rmbFrozen;
    // api切换时间
    private Integer toggleTime;
    // 调整价格
    private BigDecimal adjustPrice;
    // 成本单价
    private BigDecimal costPrice;
    // 发送错误短信后间隔时间，单位分钟
    private Integer pauseMsgTime;
    // 价格区间,(用于低于成本的场合)
    private String priceScope;


    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id",unique = true, nullable = false)
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

    @Column(name = "random")
    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }

    
    @Column(name = "ratio")
    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    
    @Column(name = "cancel_time")
    public Integer getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Integer cancelTime) {
        this.cancelTime = cancelTime;
    }

    
    @Column(name = "message_flag")
    public Boolean getMessageFlag() {
        return messageFlag;
    }

    public void setMessageFlag(Boolean messageFlag) {
        this.messageFlag = messageFlag;
    }

    
    @Column(name = "mobile_phone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }


    @Column(name = "sleep_time")
    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    
    @Column(name = "max_order")
    public Integer getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(Integer maxOrder) {
        this.maxOrder = maxOrder;
    }

    
    @Column(name = "all_xnb")
    public BigDecimal getAllXnb() {
        return allXnb;
    }

    public void setAllXnb(BigDecimal allXnb) {
        this.allXnb = allXnb;
    }

    
    @Column(name = "all_cny")
    public BigDecimal getAllCny() {
        return allCny;
    }

    public void setAllCny(BigDecimal allCny) {
        this.allCny = allCny;
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

    @Column(name = "urls")
    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public Fuser getUser() {
        return user;
    }

    public void setUser(Fuser user) {
        this.user = user;
    }

    @Column(name = "release_time")
    public Integer getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    @Column(name = "req_sleep_time")
    public Integer getReqSleepTime() {
        return reqSleepTime;
    }

    public void setReqSleepTime(Integer reqSleepTime) {
        this.reqSleepTime = reqSleepTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserve_user_id")
    public Fuser getReserveUser() {
        return reserveUser;
    }

    public void setReserveUser(Fuser reserveUser) {
        this.reserveUser = reserveUser;
    }

    @Column(name = "xnb_frozen")
    public BigDecimal getXnbFrozen() {
        return xnbFrozen;
    }

    public void setXnbFrozen(BigDecimal xnbFrozen) {
        this.xnbFrozen = xnbFrozen;
    }

    @Column(name = "rmb_frozen")
    public BigDecimal getRmbFrozen() {
        return rmbFrozen;
    }

    public void setRmbFrozen(BigDecimal rmbFrozen) {
        this.rmbFrozen = rmbFrozen;
    }

    @Column(name = "toggle_time")
    public Integer getToggleTime() {
        return toggleTime;
    }

    public void setToggleTime(Integer toggleTime) {
        this.toggleTime = toggleTime;
    }

    @Column(name = "adjust_price")
    public BigDecimal getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(BigDecimal adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    @Column(name = "cost_price")
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    @Column(name = "pause_msg_time")
    public Integer getPauseMsgTime() {
        return pauseMsgTime;
    }

    public void setPauseMsgTime(Integer pauseMsgTime) {
        this.pauseMsgTime = pauseMsgTime;
    }

    @Column(name = "price_scope")
    public String getPriceScope() {
        return priceScope;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
    }
}
