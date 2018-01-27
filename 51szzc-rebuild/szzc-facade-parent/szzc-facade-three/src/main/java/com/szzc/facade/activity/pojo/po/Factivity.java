package com.szzc.facade.activity.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Factivity extends BasePo implements Serializable {

    /**
     * 主键，对应表字段为：factivity620.fid
     */
    private Integer fid;

    /**
     * 用户id，对应表字段为：factivity620.userid
     */
    private Integer userid;

    /**
     * 今日是否签到，对应表字段为：factivity620.issign
     */
    private Boolean issign;

    /**
     * 今日交易次数，对应表字段为：factivity620.transactiontime
     */
    private Integer transactiontime;

    /**
     * 今日充值次数，对应表字段为：factivity620.chargetime
     */
    private Integer chargetime;

    /**
     * 邀请好友抽奖次数，对应表字段为：factivity620.friendsnumber
     */
    private Integer friendsnumber;

    /**
     * 冲币总金额，对应表字段为：factivity620.coinmoney
     */
    private BigDecimal coinmoney;

    /**
     * 邀请用户总数，对应表字段为：factivity620.totalfriendsnumber
     */
    private Integer totalfriendsnumber;

    /**
     * 签到获取总币数，对应表字段为：factivity620.signtotal
     */
    private Integer signtotal;

    private static final long serialVersionUID = 1L;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Boolean getIssign() {
        return issign;
    }

    public void setIssign(Boolean issign) {
        this.issign = issign;
    }

    public Integer getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(Integer transactiontime) {
        this.transactiontime = transactiontime;
    }

    public Integer getChargetime() {
        return chargetime;
    }

    public void setChargetime(Integer chargetime) {
        this.chargetime = chargetime;
    }

    public Integer getFriendsnumber() {
        return friendsnumber;
    }

    public void setFriendsnumber(Integer friendsnumber) {
        this.friendsnumber = friendsnumber;
    }

    public BigDecimal getCoinmoney() {
        return coinmoney;
    }

    public void setCoinmoney(BigDecimal coinmoney) {
        this.coinmoney = coinmoney;
    }

    public Integer getTotalfriendsnumber() {
        return totalfriendsnumber;
    }

    public void setTotalfriendsnumber(Integer totalfriendsnumber) {
        this.totalfriendsnumber = totalfriendsnumber;
    }

    public Integer getSigntotal() {
        return signtotal;
    }

    public void setSigntotal(Integer signtotal) {
        this.signtotal = signtotal;
    }
}