package com.otc.facade.user.pojo.poex;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserReportEx implements Serializable{

    private Long id;
    private String emailAddress;
    private String realName;
    private String userStatus;
    private String kycStatus;
    private String passportType;
    private String  passportNo;
    private Date createDate;
    private String registerIp;
    private Date loginTime;
    private String loginIp;
    private String identityurlOn;
    private String identityurlOff;
    private String identityurlHold;
    private String rejectionReason;
    private Date commitDate;

    //虚拟币地址查询
    private String address;  //充值地址
    private String coinname; //币种名称
    private String loginAdress; //登录地址
    private String operationType; //操作类型
    private String recordDetail; //资产记录
    private Long kycId; //kyc认证id

    public String getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(String recordDetail) {
        this.recordDetail = recordDetail;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getLoginAdress() {
        return loginAdress;
    }

    public void setLoginAdress(String loginAdress) {
        this.loginAdress = loginAdress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoinname() {
        return coinname;
    }

    public void setCoinname(String coinname) {
        this.coinname = coinname;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
