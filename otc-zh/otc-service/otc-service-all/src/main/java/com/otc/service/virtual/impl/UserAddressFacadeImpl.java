package com.otc.service.virtual.impl;

import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.service.UserAddressFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by lx on 17-4-19.
 */
public class UserAddressFacadeImpl implements UserAddressFacade {

    @Override
    public UserAddress queryUserAddress(UserAddressCond cond) {
        return OTCBizPool.getInstance().userAddressBiz.queryUserAddress(cond);
    }

    @Override
    public boolean saveUserAddress(UserAddress userAddress) {
        return OTCBizPool.getInstance().userAddressBiz.saveUserAddress(userAddress);
    }
}
