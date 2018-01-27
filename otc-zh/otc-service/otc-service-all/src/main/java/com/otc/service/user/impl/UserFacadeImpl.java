package com.otc.service.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.service.UserFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by fenggq on 17-4-20.
 */
@Service
public class UserFacadeImpl implements UserFacade{


    @Override
    public User selectByLoginName(String loginName) {
        return OTCBizPool.getInstance().userBiz.selectUserForLogin(loginName);
    }


    @Override
    public User selectById(Long userId) throws UserBizException {
        return OTCBizPool.getInstance().userBiz.select(userId);
    }

    @Override
    public User selectByIdForException(Long userId) throws Exception {
        return OTCBizPool.getInstance().userBiz.selectByIdForException(userId);
    }


    @Override
    public User updateUserPassword(String newPassword, String oldPassword, String token,String code) throws UserBizException {
        return OTCBizPool.getInstance().userBiz.updateUserPassword(newPassword, oldPassword, token,code);
    }

    @Override
    public CacheUserInfo getCacheUserInfo(String token, Long userId,String ip) throws UserBizException {
        return OTCBizPool.getInstance().userBiz.getCacheUserInfo(token,userId,ip);
    }

    @Override
    public CacheUserInfo getCacheUserInfo(Long userId) throws UserBizException {
        return OTCBizPool.getInstance().userBiz.getCacheUserInfo(userId);
    }


    @Override
    public void logout(String token) {
        UserCacheManager.logOutWithToken(token);
    }

    @Override
    public void validateAndRefreshToken(String token) {
        boolean result = UserCacheManager.validateAndRefreshWithToken(token,null);
        if (!result) {
            throw new UserBizException(10010002, "用户未登陆或token过期");
        }
    }

    @Override
    public void updateUserFishCode(Long userId, String fishCode) {
        OTCBizPool.getInstance().userBiz.updateUserFish(userId,fishCode);
    }


    @Override
    public List<Long> selectUserByFilter(String filter) {
        return OTCBizPool.getInstance().userBiz.selectUserByFilter(filter);
    }
}
