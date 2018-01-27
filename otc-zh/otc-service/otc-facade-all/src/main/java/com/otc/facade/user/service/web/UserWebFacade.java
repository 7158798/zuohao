package com.otc.facade.user.service.web;

import com.otc.core.validator.SmsType;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.poex.UserLoginInfo;
import com.otc.facade.user.pojo.poex.UserWebLoginInfo;
import com.otc.facade.user.pojo.poex.VerifyResult;
import com.otc.facade.user.service.UserFacade;

/**
 * Created by fenggq on 17-4-20.
 */
public interface UserWebFacade extends UserFacade{
    /**
     * 注册用户  ----邮箱注册
     *
     * @param loginName       注册－登录名
     * @param verificationCode    注册所需的验证码
     * @return the console
     * @throws UserBizException the console biz exception
     */
    User registerUserByEmail(String loginName, String email, String verificationCode, String pwd, String IPAddress) throws UserBizException;



    /**
     *   通过手机、密码登录
     * @param loginMobileNo
     * @param loginPwd
     * @param IPAddress
     * @param loginPlatform
     * @return
     */
    UserLoginInfo userLoginByPassword(String loginMobileNo, String loginPwd, String IPAddress, String loginPlatform);


    /**
     * 获取用户登录信息
     */
    UserLoginInfo getUserLoginInfo(User user,String IPAddress);


    /**
     * 修改用户信息
     *
     * @return int
     * @throws UserBizException the console biz exception
     */
    void updateUser(User user) throws UserBizException;


    /**
     * 忘记密码重置，验证短信验证码是否正确
     *
     * @param email
     * @param smsAuthCode the sms auth code
     * @return verify result
     * @throws UserBizException the console biz exception
     */
    VerifyResult verifySmsAuthCode(String email, String smsAuthCode, SmsType smsType) throws UserBizException;


    /**
     * 设置登录新密码
     *
     * @param email   the phone number
     * @param newPassword   the new password
     * @param authCodeCache the auth code cache
     * @return verify result
     * @throws UserBizException the console biz exception
     */
    VerifyResult resetPassword(String email, String newPassword, String authCodeCache) throws UserBizException;


    /**
     *  修改用户邮箱
     * @param userId
     * @param newEmail
     * @param authCodeCache
     * @param authCode
     * @return
     * @throws UserBizException
     */
    VerifyResult changeEmail(Long userId,String newEmail,String authCodeCache,String authCode) throws UserBizException;

    /**
     * 用户信息处理
     * @param token
     * @param userInfo
     * @return
     */
    UserWebLoginInfo getUserWebLoginInfo(String token, CacheUserInfo userInfo);

    /**
     * 用户信息处理
     * @param userInfo
     * @return
     */
    UserWebLoginInfo getUserWebLoginInfo(CacheUserInfo userInfo);


}
