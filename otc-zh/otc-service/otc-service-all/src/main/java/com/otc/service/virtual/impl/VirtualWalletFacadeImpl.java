package com.otc.service.virtual.impl;

import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualWallet;
import com.otc.facade.virtual.service.VirtualWalletFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualWalletFacadeImpl implements VirtualWalletFacade {

    @Override
    public List<VirtualWallet> queryListVirtualWallet(VirtualWalletCond cond) {
        return OTCBizPool.getInstance().virtualWalletBiz.queryListVirtualWallet(cond);
    }

    @Override
    public VirtualWallet queryVirtualWallet(VirtualWalletCond cond) {
        return OTCBizPool.getInstance().virtualWalletBiz.queryVirtualWallet(cond);
    }
}
