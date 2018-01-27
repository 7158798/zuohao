package com.otc.service.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.user.pojo.po.UserAuthentication;
import com.otc.facade.user.service.UserAuFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by a123 on 17-4-25.
 */
@Service
public class UserAuFacadeImpl implements UserAuFacade {
    @Override
    public UserAuthentication realNameAuth(Long userId, String identityNo, String identityType, String realName) {
        return OTCBizPool.getInstance().userAuBiz.realName(userId, identityNo, identityType, realName);
    }

    @Override
    public void kycAuth(Long userId, String identityurlOn, String identityurlOff, String identityurlHold) {
        OTCBizPool.getInstance().userAuBiz.kycAuth(userId, identityurlOn, identityurlOff, identityurlHold);
    }

    @Override
    public UserAuthentication getUserAuthentication(Long userId) {
        return OTCBizPool.getInstance().userAuBiz.selectByUserId(userId);
    }

    @Override
    public List<Long> selectUserByFilter(String filter) {
        return OTCBizPool.getInstance().userAuBiz.selectUserByFilter(filter);
    }
}
