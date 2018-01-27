package com.szzc.facade.api.service;

import com.szzc.facade.api.pojo.po.UserApi;

/**
 * Created by zygong on 17-7-19.
 */
public interface UserApiFacade {

    /**
     * 获取详情
     * @param apikey
     * @return
     */
    UserApi getUserApiByKey(String apikey);
}
