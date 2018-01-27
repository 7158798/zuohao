package com.base.common.login.bean;

import com.base.common.login.exception.ConnectException;
import com.jucaifu.common.util.JsonHelper;
import fr.opensagres.xdocreport.document.json.JSONObject;

/**
 * Created by zh on 16-10-9.
 */
public class QQUserInfo extends UserInfo{
    private String appId;
    public QQUserInfo(String token, String openID,String userInfoUrl,String appId) {
        super(token, openID);
        this.url = userInfoUrl;
        this.appId = appId;
    }

    QQUserInfoBean getUserBean(String openid,String userInfoUrl) throws ConnectException {
        String user = this.client.get(userInfoUrl+"?openid="+openid.trim()+"&oauth_consumer_key="+appId.trim()+"&access_token="+this.client.getToken());
        return new QQUserInfoBean(new JSONObject(user));
        //  return JsonHelper.jsonStr2Obj(info, QQUserInfoBean.class);
    }
}