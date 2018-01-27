package com.otc.facade.user.pojo.poex;

import java.io.Serializable;

public class UserWebLoginInfo implements Serializable {

    private Boolean bindPushResult;
    private String token;
    private UserInfo userInfo;


    public Boolean getBindPushResult() {
        return bindPushResult;
    }

    public void setBindPushResult(Boolean bindPushResult) {
        this.bindPushResult = bindPushResult;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
