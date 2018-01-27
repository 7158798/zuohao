package com.szzc.api.biz;

import com.facade.core.wallet.cache.CacheHelper;
import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.szzc.api.dao.UserApiMapper;
import com.szzc.facade.api.pojo.po.UserApi;
import com.szzc.facade.api.pojo.vo.UserApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lx on 17-7-22.
 */
@Service
public class UserApiBiz extends AbsBaseBiz<UserApi,UserApiVo,UserApiMapper> {
    @Autowired
    private UserApiMapper userApiMapper;
    @Override
    public UserApiMapper getDefaultMapper() {
        return userApiMapper;
    }

    public UserApi getUserApiByKey(String apikey) {
        UserApi userApi = CacheHelper.getObj(apikey);
        if (userApi == null){
            userApi = userApiMapper.getUserApiByKey(apikey);
            if (userApi != null){
                // 缓存一个小时
                CacheHelper.saveObj(apikey,userApi, TimeConstant.HALF_HOUR_UNIT_SECONDS);
            }
        }
        return userApi;
    }
}
