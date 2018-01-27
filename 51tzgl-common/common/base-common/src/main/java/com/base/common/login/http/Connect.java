package com.base.common.login.http;


/**
 * Created by zh on 16-10-7.
 */
public abstract class Connect {
    protected HttpClient client = new HttpClient();
    protected Connect() {
    }

    protected Connect(String token, String openID) {
        this.client.setToken(token);
        this.client.setOpenID(openID);
    }

    protected void setToken(String token) {
        this.client.setToken(token);
    }

    protected void setOpenID(String openID) {
        this.client.setOpenID(openID);
    }

}
