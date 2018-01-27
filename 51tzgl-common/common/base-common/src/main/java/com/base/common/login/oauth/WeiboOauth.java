package com.base.common.login.oauth;

import com.base.common.login.exception.ConnectException;
import fr.opensagres.xdocreport.document.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zh on 16-10-9.
 */
public class WeiboOauth extends Oauth {
    public WeiboOauth(){
    }

    public WeiboOauth(String appid, String appKey,String redirectUrl, String scope){
        this.appid = appid;
        this.redirectUrl = redirectUrl;
        this.scope = scope;
        this.appKey = appKey;
        this.setUrl();
    }

    @Override
    void setUrl() {
        this.authorizeURL = "https://api.weibo.com/oauth2/authorize";
        this.accessTokenURL = "https://api.weibo.com/oauth2/access_token";
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
            Matcher m = Pattern.compile("state=(\\w+)&code=(\\w+)&?").matcher(url);
            String authCode = "";
            String state = "";
            if(m.find()) {
                state = m.group(1);
                authCode = m.group(2);

            }
            return new String[]{authCode, state};
        }

    }

    @Override
    AccessToken buildAccessToken(String resource) throws ConnectException {
        JSONObject jonson = new JSONObject(resource);
        String accessToken = jonson.getString("access_token");
        String expiresIn = String.valueOf(jonson.getInt("expires_in"));
        return new AccessToken(accessToken,expiresIn);
    }

    @Override
    String getOpenId(AccessToken accessToken) throws ConnectException{
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken.getAccessToken());
        JSONObject object = new JSONObject(this.client.post(this.OpenIdUrl, params));
        return String.valueOf(object.getLong("uid"));

    }
}
