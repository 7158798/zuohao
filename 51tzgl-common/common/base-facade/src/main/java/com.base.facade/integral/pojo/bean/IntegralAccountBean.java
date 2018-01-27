package com.base.facade.integral.pojo.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zh on 16-10-23.
 */
public class IntegralAccountBean implements Serializable {
    private Long userId ;
    private String  niceName;
    private String mobileNumber;
    private String  qqAccount;
    private String weixinAccount;
    private String  weiboAccount;
    private String  rigistDate;
    private BigDecimal usableAmount;
    private BigDecimal  toCashAmount;
    private BigDecimal totalAmount;
    private String  userType;
    private String userRole;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getWeixinAccount() {
        return weixinAccount;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    public String getWeiboAccount() {
        return weiboAccount;
    }

    public void setWeiboAccount(String weiboAccount) {
        this.weiboAccount = weiboAccount;
    }

    public String getRigistDate() {
        return rigistDate;
    }

    public void setRigistDate(String rigistDate) {
        this.rigistDate = rigistDate;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getToCashAmount() {
        return toCashAmount;
    }

    public void setToCashAmount(BigDecimal toCashAmount) {
        this.toCashAmount = toCashAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
