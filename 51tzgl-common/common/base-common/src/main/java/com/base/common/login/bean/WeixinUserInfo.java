package com.base.common.login.bean;

import com.base.common.login.Response.UserResponse;
import com.base.common.login.exception.ConnectException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import fr.opensagres.xdocreport.document.json.JSONObject;

/**
 * Created by lx on 17-1-3.
 */
public class WeixinUserInfo extends UserInfo {

    public WeixinUserInfo(String token, String openID,String userInfoUrl) {
        super(token, openID);
        this.url = userInfoUrl;
    }

    @Override
    UserResponse getUserBean(String openId, String url) throws ConnectException {
        String user = this.client.get(url+"?access_token="+ this.client.getToken()+"&openid=" + openId + "&lang=zh_CN");
        LOG.i(this,user);
        WeixinUserInfoBean userInfoBean = JsonHelper.jsonStr2Obj(user,WeixinUserInfoBean.class);
        return userInfoBean;
    }
}
