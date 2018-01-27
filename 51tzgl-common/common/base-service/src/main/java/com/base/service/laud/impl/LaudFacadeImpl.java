package com.base.service.laud.impl;


import com.base.facade.laud.pojo.cond.LaudCond;
import com.base.facade.laud.pojo.po.Laud;
import com.base.facade.laud.service.LaudFacade;
import com.base.service.pool.BaseServiceBizPool;
import com.base.spi.UserLockService;
import org.springframework.stereotype.Service;

/**
 * Created by lx on 16-11-11.
 */
public class LaudFacadeImpl implements LaudFacade {

    @Override
    public Laud getLaudByCondition(LaudCond cond) {
        return BaseServiceBizPool.getInstance().laudBiz.getLaudByCondition(cond);
    }

    @Override
    public Laud getLaudByCondition(Long relationId, String type, Long userId, String ipAddress,String macAddress) {
        return BaseServiceBizPool.getInstance().laudBiz.getLaudByCondition(relationId, type, userId, ipAddress,macAddress);
    }

    @Override
    public boolean cancelLaud(Laud laud) {
        return BaseServiceBizPool.getInstance().laudBiz.cancelLaud(laud);
    }

    @Override
    public boolean saveLaud(Laud laud) {
        return BaseServiceBizPool.getInstance().laudBiz.saveLaud(laud);
    }

    @Override
    public void mergeLaud(Long sourcesUserId, Long targetUserId) {
        BaseServiceBizPool.getInstance().laudBiz.mergeLaud(sourcesUserId, targetUserId);
    }
}
