package com.otc.facade.user.pojo.poex;



import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.pojo.po.UserCreditRecord;

import java.io.Serializable;

public class CacheUserInfo implements Serializable {

    private User baseInfo;

    private String loginIp;

    private String loginAddress;

    private UserAuthentication userAuthentication;

    private UserCreditRecord userCreditRecord;

    public UserCreditRecord getUserCreditRecord() {
        return userCreditRecord;
    }

    public void setUserCreditRecord(UserCreditRecord userCreditRecord) {
        this.userCreditRecord = userCreditRecord;
    }

    /**
     * 获取用户id
     *
     * @return
     */
    public String getUid() {
        String uid = null;
        if (baseInfo != null) {
            uid = String.valueOf(baseInfo.getId());
        }
        return uid;
    }

    public static CacheUserInfo newWithUserEx(User baseInfo) {
        CacheUserInfo cacheUserInfo = new CacheUserInfo();
        cacheUserInfo.baseInfo = baseInfo;
        return cacheUserInfo;
    }

    public User getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(User baseInfo) {
        this.baseInfo = baseInfo;
    }


    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }


    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }
}
