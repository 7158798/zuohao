package com.otc.api.console.ctrl.user.reponse;

import com.jucaifu.common.pojo.vo.BaseResp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggq on 17-4-26.
 */
public class UserListResp extends BaseResp {

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
    private String hasRealName;
    private String hasKyc;

    public String getHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(String hasRealName) {
        this.hasRealName = hasRealName;
    }

    public String getHasKyc() {
        return hasKyc;
    }

    public void setHasKyc(String hasKyc) {
        this.hasKyc = hasKyc;
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
