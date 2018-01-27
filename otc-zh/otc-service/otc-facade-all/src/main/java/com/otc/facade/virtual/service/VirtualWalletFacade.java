package com.otc.facade.virtual.service;

import com.otc.facade.virtual.pojo.cond.VirtualWalletCond;
import com.otc.facade.virtual.pojo.po.VirtualWallet;

import java.util.List;

/**
 * Created by lx on 17-4-19.
 */
public interface VirtualWalletFacade {

    List<VirtualWallet> queryListVirtualWallet(VirtualWalletCond cond);

    VirtualWallet queryVirtualWallet(VirtualWalletCond cond);
}
