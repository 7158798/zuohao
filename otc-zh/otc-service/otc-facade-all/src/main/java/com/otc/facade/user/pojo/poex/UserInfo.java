package com.otc.facade.user.pojo.poex;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fenggq on 17-4-20.
 */
public class UserInfo implements Serializable{

    private String loginName;

    private String email;

    private String realName;

    private Long id;

    private Date registDate;

    private boolean hasRealName; //实名认证

    private String kycStatus;  //认证状态

    private Integer tradeTimes; //交易次数

    private BigDecimal goodJudgeRate; //好评率

    private Integer goodJudgeTimes;//好评数

    private Integer badJudgeTimes;//差评次数

    private Integer commonJudgeTimes; //中评次数

    private Integer confirmMinute;// 平均放行时间

    private Date lastTradeTime;//最后交易时间

    private String fishCode;//防钓鱼码

    private String rejectionReason;

    public String getFishCode() {
        return fishCode;
    }

    public void setFishCode(String fishCode) {
        this.fishCode = fishCode;
    }

    public Date getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(Date lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Integer getConfirmMinute() {
        return confirmMinute;
    }

    public void setConfirmMinute(Integer confirmMinute) {
        this.confirmMinute = confirmMinute;
    }

    public Integer getGoodJudgeTimes() {
        return goodJudgeTimes;
    }

    public void setGoodJudgeTimes(Integer goodJudgeTimes) {
        this.goodJudgeTimes = goodJudgeTimes;
    }

    public boolean isHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(boolean hasRealName) {
        this.hasRealName = hasRealName;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public Integer getTradeTimes() {
        return tradeTimes;
    }

    public void setTradeTimes(Integer tradeTimes) {
        this.tradeTimes = tradeTimes;
    }

    public BigDecimal getGoodJudgeRate() {
        return goodJudgeRate;
    }

    public void setGoodJudgeRate(BigDecimal goodJudgeRate) {
        this.goodJudgeRate = goodJudgeRate;
    }

    public Integer getBadJudgeTimes() {
        return badJudgeTimes;
    }

    public void setBadJudgeTimes(Integer badJudgeTimes) {
        this.badJudgeTimes = badJudgeTimes;
    }

    public Integer getCommonJudgeTimes() {
        return commonJudgeTimes;
    }

    public void setCommonJudgeTimes(Integer commonJudgeTimes) {
        this.commonJudgeTimes = commonJudgeTimes;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
