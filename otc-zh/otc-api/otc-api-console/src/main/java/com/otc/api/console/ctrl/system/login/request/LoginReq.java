package com.otc.api.console.ctrl.system.login.request;

/**
 * Created by zhaiyz on 15-11-2.
 */
public class LoginReq {

    /**
     * 登录名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
