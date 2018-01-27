package com.szzc.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.szzc.facade.api.pojo.po.UserApi;
import com.szzc.facade.api.service.UserApiFacade;
import com.szzc.pool.ThreeBizPool;
import org.springframework.stereotype.Component;

/**
 * Created by lx on 17-7-22.
 */
@Component
@Service
public class UserApiFacadeImpl implements UserApiFacade{

    @Override
    public UserApi getUserApiByKey(String apikey) {
        return ThreeBizPool.getInstance().userApiBiz.getUserApiByKey(apikey);
    }
}
