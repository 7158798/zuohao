package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by fenggq on 17-4-20.
 */
public interface WebApiUser extends ApiBasePathConstant {
    /**
     * Web 用户模块接口前缀
     */
    String PREFIX_WEB_API =  WEB_API+MODULE_USER;


    /**
     * 获取图形验证码
     */
    String WEB_VALIDATE_API = PREFIX_WEB_API+"/validateimg"+ PATH_QUERY_JSON;

    /**
     * 获取注册验证码
     */
    String WEB_REGISTER_EMAILCODE_API = PREFIX_WEB_API + "/registCode";


    /**
     * 获取验证码通用接口
     */
    String WEB_SEND_EMAILCODE_API = PREFIX_WEB_API+"/senEmailCode";


    /**
     * 用户重置密码短信
     */
    String RESET_PASSWORD_SMS_API = PREFIX_WEB_API + "/sendResetpwd";


    /**
     * 修改密码验证码
     */
    String UPDATE_PASSWORD_SMS_API = PREFIX_WEB_API+"/sendPwdCode";


    /**
     * 修改邮箱验证码
     */
    String CHANGE_EMAIL_SMS_API = PREFIX_WEB_API+"/changeEamilCode";

    /**
     * WEB  注册
     */
    String WEB_REGIST_API = PREFIX_WEB_API + "/regist" ;

    /**
     * 校验用户名重复
     */
    String WEB_CHECKLOGINNAME_API = PREFIX_WEB_API + "/checkLoginName";

    /**
     * WEB用户登陆
     */
    String WEB_LOGIN_API = PREFIX_WEB_API + "/login";

    /*
    * 用户信息刷新
    */
    String USER_REFRESH_API = PREFIX_WEB_API + "/refreshuser" + PATH_QUERY_JSON;


    /**
     * WEB退出
     */
    String WEB_LOGOUT_API = PREFIX_WEB_API + "/logout";

    /**
     * WEB忘记登录密码,重置密码
     */
    String WEB_RESET_PASSWORD_API = PREFIX_WEB_API + "/resetpwd";


    /**
     * Web 修改登录密码
     */
    String WEB_UPDATE_PASSWORD_API = PREFIX_WEB_API+"/updatepwd";

    /**
     * 修改邮箱
     */
    String WEB_UPDATE_EMAIL_API = PREFIX_WEB_API+"/changeEmail";


    /**
     * 设置、修改防钓鱼码
     */
    String WEB_UPDATE_FISHCODE_API = PREFIX_WEB_API+"/updatefishcode";


    /**
     * 校验并刷新token
     */
    String WEB_VALIDATE_REFRESH_TOKEN_API = PREFIX_WEB_API + "/valandref";

    /**
     * 实名认证
     */
    String WEB_REALNAME_API = PREFIX_WEB_API+"/realName";

    /**
     * KYC认证
     */
    String WEB_KYC_POST_API = PREFIX_WEB_API+"/kycPost";

    /**
     * 获取实名认证信息
     */
    String WEB_GET_REALNAME_INFO = PREFIX_WEB_API+"/realinfo"+PATH_QUERY_JSON;


    /**
     * 获取用户未读消息、交易数
     */
    String WEB_GET_MESSAGE_NOREAD = PREFIX_WEB_API+"/noReadInfo"+PATH_QUERY_JSON;
}
