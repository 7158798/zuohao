package com.base.common.login.bean;

import com.base.common.login.Response.UserResponse;
import com.base.common.login.exception.ConnectException;
import fr.opensagres.xdocreport.document.json.JSONObject;

/**
 * Created by zh on 16-10-10.
 */
public class WeiboUserInfo extends UserInfo{
    public WeiboUserInfo(String token, String openID,String userInfoUrl) {
        super(token, openID);
        this.url = userInfoUrl;
    }

    UserResponse getUserBean(String openid,String userInfoUrl) throws ConnectException {
        String user = this.client.get(userInfoUrl+"?access_token="+this.client.getToken()+"&uid="+openid.trim());
        return new WeiboUserInfoBean(new JSONObject(user));
    }
}
