package com.otc.service.user.biz;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.IpHelper;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.core.cache.UserCacheManager;
import com.otc.facade.user.enums.UserOperationEnum;
import com.otc.facade.user.exceptions.UserBizException;
import com.otc.facade.user.pojo.po.User;
import com.otc.facade.user.pojo.po.UserOperation;
import com.otc.facade.user.pojo.poex.CacheUserInfo;
import com.otc.facade.user.pojo.vo.UserOperationVo;
import com.otc.service.user.dao.UserOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-2.
 */
@Service
public class UserOperationBiz extends AbsBaseBiz<UserOperation,UserOperationVo,UserOperationMapper> {

    @Autowired
    private UserOperationMapper userOperationMapper;

    @Override
    public UserOperationMapper getDefaultMapper() {
        return userOperationMapper;
    }


    /**
     * 增加操作日志
     * @param userId
     * @param operationEnum
     */
    public void add(Long userId, UserOperationEnum operationEnum){
        LOG.dStart(this,"增加用户操作日志");
        if(userId == null || operationEnum == null){
            throw new UserBizException("请求参数异常");
        }

        CacheUserInfo cacheUserInfo = UserCacheManager.getCacheObj(userId);

        String ip = cacheUserInfo == null?"":cacheUserInfo.getLoginIp();

        String ipAddress = IpHelper.getAddress(ip);
        UserOperation userOperation = new UserOperation();
        userOperation.setUserId(userId);
        userOperation.setCreateDate(new Date());
        userOperation.setLoginAdress(ipAddress);
        userOperation.setLoginIp(ip);
        userOperation.setOperationType(operationEnum.getCode());
        this.insert(userOperation);

        LOG.dEnd(this,"增加用户操作日志");
    }

}
