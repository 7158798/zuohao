package com.otc.facade.virtual.service.web;

import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.service.UserAddressFacade;

/**
 * Created by lx on 17-4-19.
 */
public interface UserAddressWebFacade extends UserAddressFacade {

    UserAddress bindUserAddress(Long userId,Long coinId);
}
