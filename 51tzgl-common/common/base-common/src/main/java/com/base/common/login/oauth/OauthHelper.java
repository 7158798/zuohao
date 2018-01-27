package com.base.common.login.oauth;

import com.base.common.login.Response.UserResponse;
import com.base.common.login.bean.*;
import com.base.common.login.exception.ConnectException;

import javax.servlet.ServletRequest;

/**
 * Created by zh on 16-10-11.
 */
public class OauthHelper {
    private static  final String weiboAppId = "3858420591";
    private static final String weiboAppKey = "c61015f0917ce536638900d43aaffc73";
    private static final String weiboRedirectUrl = "http://127.0.0.1:8080/user/weiboredrict";
    private static final String weiboScope = "all";
    private static final String WEIBO_TOKEN_URL="https://api.weibo.com/oauth2/get_token_info";
    private static final String WEIBO_GET_USERINFO_URL="https://api.weibo.com/2/users/show.json";

    private static  final String QQAppId = "1105753144";
    private static final String QQAppKey = "dhCGie8AbQRdUy5i";
    private static final String QQRedirectUrl = "http://localhost:8080/user/qqredrict";
    private static final String QQScope = "get_user_info";
    private static final String QQ_GET_USERINFO_URL = "https://graph.qq.com/user/get_user_info";
    private static final String QQ_TOKEN_URL = "https://graph.qq.com/oauth2.0/me";

    /*// 公众号的唯一标识
    private static final String WEIXIN_APPID = "wx071e2de32fb3bc18";
    // 公众号的appsecret
    private static final String WEIXIN_SECRET = "00693d0dc32b9b3c401192efda3e2159";*/
    // 获取用户信息
    private static final String WEIXIN_GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    private static final String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String weixin_TOKEN_URL = "https://api.weixin.qq.com/sns/auth";


    public static String getWeiboAuthorizeURL(ServletRequest request) throws ConnectException{
        return getWeiboOauth().getAuthorizeURL(request);
    }

    public static Oauth getWeiboOauth() throws ConnectException{
        return new WeiboOauth(weiboAppId,weiboAppKey,weiboRedirectUrl,weiboScope);
    }

    public static String getOpenId(String accessToken,Integer thirdType)throws ConnectException{

        if(thirdType == 1){
            return new OpenID(accessToken,QQ_TOKEN_URL,thirdType).getUserOpenID();
        }else if(thirdType ==3){
            return new OpenID(accessToken,WEIBO_TOKEN_URL,thirdType).getUserOpenID();
        }
        return "";
    }



    public static UserResponse getWeiBoUserInfo(String accessToken,String openID)throws ConnectException{
        return new WeiboUserInfo(accessToken, openID,WEIBO_GET_USERINFO_URL).getUserInfo();
    }


    public static String getQQAuthorizeURL(ServletRequest request) throws ConnectException{
        return getQQOauth().getAuthorizeURL(request);
    }

    public static Oauth getQQOauth() throws ConnectException{
        return new QQOauth(QQAppId,QQAppKey,QQRedirectUrl,QQScope);
    }


    public static UserResponse getQQUserInfo(String accessToken,String openId) throws ConnectException{
        return new QQUserInfo(accessToken,openId,QQ_GET_USERINFO_URL,QQAppId).getUserInfo();
    }



    ////////微信
    public static Boolean validWexinToken(String accessToken,String openId) throws ConnectException{
        return new WeiXinOauth().validAccessToken(openId,accessToken,weixin_TOKEN_URL);
    }

    public static WeixinAccessTokenBean getWeiXinAccessToken(String wxAppid,String wxSecret,String code) throws ConnectException {
        return new WeiXinOauth().getWeiXinAccessToken(wxAppid,wxSecret,code,WEIXIN_ACCESS_TOKEN_URL);
    }


    public static UserResponse getWeixinUserInfo(String accessToken,String openId) throws ConnectException{
        return new WeixinUserInfo(accessToken,openId,WEIXIN_GET_USERINFO_URL).getUserInfo();
    }




}
