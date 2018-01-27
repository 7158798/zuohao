package com.base.common.login.bean;


import com.base.common.login.http.Connect;
import com.base.common.login.exception.ConnectException;
import fr.opensagres.xdocreport.document.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zh on 16-10-8.
 */
public class OpenID extends Connect {
    private String url = "";
    private Integer type;

    public OpenID(String token,String url,Integer type) {
        this.client.setToken(token);
        this.url = url;
        this.type = type;
    }

    private String getUserOpenID(String accessToken) throws ConnectException {
        String openid = "";
        String jsonp = "";
        if(this.type == 1){
            //QQ
            jsonp = this.client.get(url + "?access_token=" + accessToken);
            Matcher m = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"").matcher(jsonp);
            if(m.find()) {
                openid = m.group(1);
            } else {
                throw new ConnectException("获取用户授权失败!");
            }
        }else if(this.type == 3){
            Map<String,String> params = new HashMap<>();
            params.put("access_token",accessToken);
            jsonp = this.client.post(url, params);
            JSONObject object = new JSONObject(jsonp);
            Long openId = object.getLong("uid");
            openid = String.valueOf(openId);
        }
        return openid;
    }



    public String getUserOpenID() throws ConnectException {
        String accessToken = this.client.getToken();
        return this.getUserOpenID(accessToken);
    }
}
