package com.otc.api.console.ctrl.user.reponse;

import com.jucaifu.common.pojo.vo.BaseResp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserKycListResp extends BaseResp {

    private Long id;
    private String registerType;

    private String emailAddress;

    private String realName;

    private String userStatus;

    private String kycStatus;

    private String hasRealName;

    private String passportType;

    private String  passportNo;

    private Date createDate;

    private String registerIp;

    private Date loginTime;

    private Date commitDate;

    private String identityurlOn;

    private String identityurlOff;

    private String identityurlHold;

    private String rejectionReason;

    private Long kycId;

    public String getHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(String hasRealName) {
        this.hasRealName = hasRealName;
    }

    public Long getKycId() {
        return kycId;
    }

    public void setKycId(Long kycId) {
        this.kycId = kycId;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getIdentityurlOn() {
        return identityurlOn;
    }

    public void setIdentityurlOn(String identityurlOn) {
        this.identityurlOn = identityurlOn;
    }

    public String getIdentityurlOff() {
        return identityurlOff;
    }

    public void setIdentityurlOff(String identityurlOff) {
        this.identityurlOff = identityurlOff;
    }

    public String getIdentityurlHold() {
        return identityurlHold;
    }

    public void setIdentityurlHold(String identityurlHold) {
        this.identityurlHold = identityurlHold;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

}
