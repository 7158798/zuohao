package com.base.service.user.impl;

import com.base.facade.user.pojo.po.UserLogin;
import com.base.facade.user.service.UserLoginFacade;
import com.base.service.pool.BaseServiceBizPool;

/**
 * @author luwei
 * @Date 12/27/16 5:52 PM
 */
public class UserLoginFacadeImpl implements UserLoginFacade{

    @Override
    public void addUserLogin(Long userId, String loginPlatform, String adressIP, String status) {
        BaseServiceBizPool.getInstance().userLoginBiz.addUserLogin(userId, loginPlatform, adressIP, status);
    }

    @Override
    public int changeUserId(Long userId, Long thirdId) {
        return BaseServiceBizPool.getInstance().userLoginBiz.changeUserId(userId, thirdId);
    }

    @Override
    public UserLogin getLastLogin(Long userId) {
        return BaseServiceBizPool.getInstance().userLoginBiz.getLastLogin(userId);
    }

    @Override
    public Boolean queryIsAppLogin(Long userId) {
        return BaseServiceBizPool.getInstance().userLoginBiz.queryIsAppLogin(userId);
    }
}
