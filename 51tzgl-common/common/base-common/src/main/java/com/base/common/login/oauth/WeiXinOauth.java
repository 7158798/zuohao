package com.base.common.login.oauth;

import com.base.common.login.bean.WeixinAccessTokenBean;
import com.base.common.login.exception.ConnectException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import fr.opensagres.xdocreport.document.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fenggq on 16-11-22.
 */
public class WeiXinOauth extends Oauth{
    @Override
    String getAuthorizeParams(String state) {
        String isScope = scope != null && !scope.equals("")?"&scope=" + scope:"";
        return "?appid=" + this.appid.trim() + "&redirect_uri=" + this.redirectUrl.trim() + "&response_type=" + "code" + "&state=" + state+isScope;
    }

    @Override
    String[] extractionAuthCodeFromUrl(String url) throws ConnectException {
        if(url == null) {
            throw new ConnectException("you pass a null String object");
        } else {
            Matcher m = Pattern.compile("code=(\\w+)&state=(\\w+)&?").matcher(url);
            String authCode = "";
            String state = "";
            if(m.find()) {
                authCode = m.group(1);
                state = m.group(2);
            }
            return new String[]{authCode, state};
        }
    }

    @Override
    Map<String, String> buildAccessTokenParam(String returnAuthCode) {
        Map<String,String> params = new HashMap<>();
        params.put("client_id",appid);
        params.put("client_secret",appKey);
        params.put("grant_type","authorization_code");
        params.put("code",returnAuthCode);
        params.put("redirect_uri", redirectUrl);
        return params;
    }

    @Override
    AccessToken buildAccessToken(String resource) throws ConnectException {
        if(resource == null) {
            throw new ConnectException("you pass a null String object");
        } else {
            Matcher m = Pattern.compile("access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)&?").matcher(resource);
            String accessToken = "";
            String expiresIn = "";
            String refreshToken = "";
            if(m.find()) {
                accessToken = m.group(1);
                expiresIn = m.group(2);
                refreshToken = m.group(3);
            }
            return new AccessToken(accessToken, expiresIn,refreshToken);
        }
    }

    @Override
    String getOpenId(AccessToken accessToken) throws ConnectException {
        return null;
    }

    @Override
    void setUrl() {
         this.accessTokenURL = "https://open.weixin.qq.com/connect/qrconnect";
    }


    public Boolean validAccessToken(String openId,String accessToken,String url){
        String jsonp = this.client.get(url + "?access_token=" + accessToken+"&openid="+openId);
        JSONObject object = new JSONObject(jsonp);
        return object.getLong("errcode") == 0;

    }

    /**
     * 获取微信accessToken
     * @param appid
     * @param secret
     * @param code
     * @param url
     * @return
     */
    public WeixinAccessTokenBean getWeiXinAccessToken(String appid,String secret,String code,String url){
        String result = this.client.get(url + "?appid=" + appid+"&secret="+secret + "&code="+ code + "&grant_type=authorization_code");
        LOG.i(this,result);
        WeixinAccessTokenBean bean = JsonHelper.jsonStr2Obj(result, WeixinAccessTokenBean.class);
        return bean;
    }
}
