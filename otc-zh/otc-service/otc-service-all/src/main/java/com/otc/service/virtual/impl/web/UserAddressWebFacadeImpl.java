package com.otc.service.virtual.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.virtual.pojo.po.UserAddress;
import com.otc.facade.virtual.service.web.UserAddressWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.virtual.impl.UserAddressFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-4-19.
 */
@Component
@Service
public class UserAddressWebFacadeImpl extends UserAddressFacadeImpl implements UserAddressWebFacade {

    @Override
    public UserAddress bindUserAddress(Long userId, Long coinId) {
        return OTCBizPool.getInstance().userAddressBiz.bindUserAddress(userId, coinId);
    }
}
