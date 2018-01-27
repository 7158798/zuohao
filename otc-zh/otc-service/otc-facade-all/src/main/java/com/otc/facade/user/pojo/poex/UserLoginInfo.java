package com.otc.facade.user.pojo.poex;

import java.io.Serializable;

public class UserLoginInfo implements Serializable {

    private String token;

    private CacheUserInfo cacheUserInfo = new CacheUserInfo();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CacheUserInfo getCacheUserInfo() {
        return cacheUserInfo;
    }

    public void setCacheUserInfo(CacheUserInfo cacheUserInfo) {
        this.cacheUserInfo = cacheUserInfo;
    }
}
