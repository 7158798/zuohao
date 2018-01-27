package com.base.common.login.oauth;

import com.base.common.login.exception.ConnectException;
import com.base.common.login.http.Connect;
import com.base.common.login.http.RandomStatusGenerator;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zh on 16-10-9.
 */
public abstract class Oauth extends Connect{
    protected  String accessTokenURL = "https://graph.qq.com/oauth2.0/token";
    protected  String authorizeURL = "https://graph.qq.com/oauth2.0/authorize";

    protected String appid;
    protected String redirectUrl;
    protected String scope;
    protected String appKey;
    protected String OpenIdUrl;

    abstract String getAuthorizeParams(String state);

    abstract String[] extractionAuthCodeFromUrl(String url)throws ConnectException;

    abstract Map<String,String> buildAccessTokenParam(String returnAuthCode);

    abstract AccessToken buildAccessToken(String resource) throws ConnectException;

    abstract String getOpenId(AccessToken accessToken) throws ConnectException;

    abstract void setUrl();


    public String getAuthorizeURL(ServletRequest request) throws ConnectException {
        String state = RandomStatusGenerator.getUniqueState();
        ((HttpServletRequest)request).getSession().setAttribute("connect_state", state);
        return authorizeURL + getAuthorizeParams(state);
    }

    public void setGetOpenIdUrl(String OpenIdUrl) {
        this.OpenIdUrl = OpenIdUrl;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    public AccessToken getAccessTokenByRequest(ServletRequest request) throws Exception {
        String queryString = ((HttpServletRequest)request).getQueryString();
        if(queryString == null) {
            return new AccessToken();
        } else {
            String state = (String)((HttpServletRequest)request).getSession().getAttribute("connect_state");
            state = "1";
            if(state != null && !state.equals("")) {
                String[] authCodeAndState = this.extractionAuthCodeFromUrl(queryString);
                String returnState = authCodeAndState[1];
                String returnAuthCode = authCodeAndState[0];
                AccessToken accessTokenObj = null;
                returnState = "1";
                if(!returnState.equals("") && !returnAuthCode.equals("")) {
                    if(!state.equals(returnState)) {
                        accessTokenObj = new AccessToken();
                    } else {
                        accessTokenObj = buildAccessToken(this.client.post(accessTokenURL,buildAccessTokenParam(returnAuthCode)));
                    }
                } else {
                    accessTokenObj = new AccessToken();
                }
                return accessTokenObj;
            } else {
                return new AccessToken();
            }
        }
    }



}
