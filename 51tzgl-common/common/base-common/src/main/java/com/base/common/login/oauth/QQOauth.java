package com.base.common.login.oauth;

import com.base.common.login.exception.ConnectException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zh on 16-10-7.
 */
public class QQOauth extends Oauth {

    public QQOauth(){
    }

    public QQOauth(String appid, String appKey,String redirectUrl, String scope){
        this.appid = appid;
        this.redirectUrl = redirectUrl;
        this.scope = scope;
        this.appKey = appKey;
        setUrl();
    }

    @Override
    void setUrl() {
        this.accessTokenURL = "https://graph.qq.com/oauth2.0/token";
        this.authorizeURL = "https://graph.qq.com/oauth2.0/authorize";
    }

    @Override
    String getAuthorizeParams(String state) {
        String isScope = scope != null && !scope.equals("")?"&scope=" + scope:"";
        return "?client_id=" + this.appid.trim() + "&redirect_uri=" + this.redirectUrl.trim() + "&response_type=" + "code" + "&state=" + state+isScope;
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
    String[] extractionAuthCodeFromUrl(String url) throws ConnectException{
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
    String getOpenId(AccessToken accessToken) throws ConnectException{
        return null;
    }
}
