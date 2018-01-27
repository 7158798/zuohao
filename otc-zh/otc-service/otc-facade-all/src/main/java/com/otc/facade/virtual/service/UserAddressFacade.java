package com.otc.facade.virtual.service;

import com.otc.facade.virtual.pojo.cond.UserAddressCond;
import com.otc.facade.virtual.pojo.po.UserAddress;

/**
 * Created by lx on 17-4-19.
 */
public interface UserAddressFacade {

    UserAddress queryUserAddress(UserAddressCond cond);

    boolean saveUserAddress(UserAddress userAddress);
}
