package com.otc.service.user.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.core.validator.SmsType;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.poex.UserLoginInfo;
import com.otc.facade.user.pojo.poex.UserWebLoginInfo;
import com.otc.facade.user.pojo.poex.VerifyResult;
import com.otc.facade.user.service.web.UserWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.user.impl.UserFacadeImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by fenggq on 17-4-20.
 */
@Component
@Service
public class UserWebFacadeImpl extends UserFacadeImpl implements UserWebFacade{
    @Override
    public User registerUserByEmail(String loginName, String email, String verificationCode, String pwd, String IPAddress) throws UserBizException {
        return OTCBizPool.getInstance().userBiz.registerUser(loginName, email, verificationCode, pwd,IPAddress);
    }

    @Override
    public UserLoginInfo userLoginByPassword(String loginName, String loginPwd, String IPAddress, String loginPlatform) {
        return OTCBizPool.getInstance().userBiz.userLoginByPassword(loginName,loginPwd,IPAddress,"WEB");
    }

    @Override
    public UserLoginInfo getUserLoginInfo(User user, String IPAddress) {
        return OTCBizPool.getInstance().userBiz.getUserLoginInfo(user,new Date(),IPAddress);
    }

    @Override
    public void updateUser(User user) throws UserBizException {
       OTCBizPool.getInstance().userBiz.update(user);
    }

    @Override
    public VerifyResult verifySmsAuthCode(String email, String smsAuthCode, SmsType smsType) throws com.otc.facade.user.exceptions.UserBizException {
        return OTCBizPool.getInstance().userBiz.verifySmsAuthCode(email, smsAuthCode, smsType);
    }

    @Override
    public VerifyResult resetPassword(String email, String newPassword, String authCodeCache) throws com.otc.facade.user.exceptions.UserBizException {
        return OTCBizPool.getInstance().userBiz.resetPassword(email, newPassword, authCodeCache);
    }

    @Override
    public VerifyResult changeEmail(Long userId, String newEmail, String authCodeCache, String authCode) throws com.otc.facade.user.exceptions.UserBizException {
        return OTCBizPool.getInstance().userBiz.changeEmail(userId, newEmail, authCodeCache, authCode);
    }


    @Override
    public UserWebLoginInfo getUserWebLoginInfo(String token, CacheUserInfo userInfo) {
        return OTCBizPool.getInstance().userBiz.getUserWebLoginInfo(token, userInfo);
    }

    @Override
    public UserWebLoginInfo getUserWebLoginInfo(CacheUserInfo userInfo) {
        return OTCBizPool.getInstance().userBiz.getUserWebLoginInfo(userInfo);
    }
}
