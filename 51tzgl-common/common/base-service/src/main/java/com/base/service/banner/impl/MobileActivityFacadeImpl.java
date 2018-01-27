package com.base.service.banner.impl;


import com.base.facade.banner.MobileActivityFacade;
import com.base.facade.banner.pojo.po.Activity;
import com.base.facade.exception.BaseCommonBizException;
import com.base.service.pool.BaseServiceBizPool;

/**
 * Created by yangyy on 15-12-5.
 */
public class MobileActivityFacadeImpl implements MobileActivityFacade {

    @Override
    public Activity getActivityById(String uuid) throws BaseCommonBizException {
        return BaseServiceBizPool.getInstance().mobileActivityBiz.select(uuid);
    }
}
