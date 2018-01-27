package com.base.facade.integral.pojo.bean;

import com.base.facade.integral.enums.IntegralType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zh on 16-10-23.
 */
public class IntegralDetailBean implements Serializable {
    private Long userId ;
    private String  niceName;
    private String mobileNumber;
    private String  qqAccount;
    private String weixinAccount;
    private String  weiboAccount;
    private String  rigistDate;
    private BigDecimal operateAmount;
    private String type;
    private String userRole;

    public BigDecimal getOperateAmount() {
        return operateAmount;
    }

    public void setOperateAmount(BigDecimal operateAmount) {
        this.operateAmount = operateAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(int type) {
        this.type =IntegralType.getDescByType(type);
    }

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





    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
