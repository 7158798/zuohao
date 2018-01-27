package com.szzc.fentrustLog.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.szzc.facade.virtualCoin.service.VirtualCoinFacade;
import com.szzc.pool.ThreeBizPool;

import java.util.Map;

/**
 * Created by zygong on 17-7-21.
 */
@Service
public class VirtualCoinTypeFacadeImpl implements VirtualCoinFacade {

    @Override
    public Map<String, Long> getActionCoinShortNameAndFtId() {
        return ThreeBizPool.getInstance().virtualCoinTypeBiz.getActionCoinShortNameAndFtId();
    }
}
