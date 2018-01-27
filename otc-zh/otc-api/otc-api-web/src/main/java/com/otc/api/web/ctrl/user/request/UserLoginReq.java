package com.otc.api.web.ctrl.user.request;

import java.io.Serializable;

/**
 * Created by fenggq on 15-11-18.
 */
public class UserLoginReq implements Serializable {

    private String loginName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
