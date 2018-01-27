package com.otc.api.console.ctrl.user.reponse;

import com.jucaifu.common.pojo.vo.BaseResp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fenggq on 17-5-3.
 */
public class UserOperationListResp extends BaseResp {



    private String operationType; //操作类型
    private Long id;
    private String registType;//注册类型
    private String emailAddress;
    private String realName;
    private String userStatus;
    private String kycStatus;
    private Date createDate;
    private String loginAdress; //登录地址
    private String loginIp;
    private String hasRealName;

    public String getHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(String hasRealName) {
        this.hasRealName = hasRealName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistType() {
        return registType;
    }

    public void setRegistType(String registType) {
        this.registType = registType;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLoginAdress() {
        return loginAdress;
    }

    public void setLoginAdress(String loginAdress) {
        this.loginAdress = loginAdress;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
