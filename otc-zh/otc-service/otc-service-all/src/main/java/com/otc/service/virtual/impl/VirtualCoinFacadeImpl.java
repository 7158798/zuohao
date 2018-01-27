package com.otc.service.virtual.impl;

import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;
import com.otc.facade.virtual.service.VirtualCoinFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualCoinFacadeImpl implements VirtualCoinFacade {

    @Override
    public VirtualCoin isExist(Long id) {
        return OTCBizPool.getInstance().virtualCoinBiz.isExist(id);
    }

    @Override
    public Map<Long, VirtualCoin> getVirtualCoin() {
        return OTCBizPool.getInstance().virtualCoinBiz.getVirtualCoin(Boolean.FALSE);
    }

    @Override
    public Map<Long, VirtualCoin> getAllVirtualCoin() {
        return OTCBizPool.getInstance().virtualCoinBiz.getVirtualCoin(Boolean.TRUE);
    }
    @Override
    public List<Withdrawfees> getFees(Long coinId) {
        return OTCBizPool.getInstance().virtualCoinBiz.getFees(coinId);
    }
}
