package com.base.facade.integral.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by yangyy on 16-4-29.
 */
public class UserIntegralAccountVo extends BasePageVo {

    private String mobileNumber;

    private String niceName;

    private String externalAccount;

    private int userType;

    private int userRole;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getExternalAccount() {
        return externalAccount;
    }

    public void setExternalAccount(String externalAccount) {
        this.externalAccount = externalAccount;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
