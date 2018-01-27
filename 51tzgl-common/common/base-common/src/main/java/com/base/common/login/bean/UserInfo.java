package com.base.common.login.bean;

/**
 * Created by zh on 16-10-8.
 */

import com.base.common.login.Response.UserResponse;
import com.base.common.login.exception.ConnectException;
import com.base.common.login.http.Connect;

public abstract class UserInfo extends Connect {
    protected String url;


    public UserInfo(String token, String openID) {
        super(token, openID);
    }

    abstract UserResponse getUserBean(String openId,String url) throws ConnectException;

    public UserResponse getUserInfo() throws ConnectException {
        return this.getUserBean(this.client.getOpenID(), url);
    }
}