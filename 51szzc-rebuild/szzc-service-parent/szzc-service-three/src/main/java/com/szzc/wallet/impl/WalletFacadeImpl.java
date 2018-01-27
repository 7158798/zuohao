package com.szzc.wallet.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jucaifu.common.log.LOG;
import com.szzc.facade.wallet.pojo.cond.VirtualWalletCond;
import com.szzc.facade.wallet.pojo.dto.TradeAccountInfoTotalMoney;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;
import com.szzc.facade.wallet.service.WalletFacade;
import com.szzc.pool.ThreeBizPool;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zygong on 17-7-19.
 */
@Component
@Service
public class WalletFacadeImpl implements WalletFacade {

    @Override
    public VirtualWallet queryWalletByCoinAndUser(Integer userId, Integer coinId) {
        return ThreeBizPool.getInstance().virtualWalletBiz.queryWalletByCoinAndUser(userId, coinId);
    }

    @Override
    public List<VirtualWallet> queryListByCondition(VirtualWalletCond cond) {
        return ThreeBizPool.getInstance().virtualWalletBiz.queryListByCondition(cond);
    }

    @Override
    public int updateWallet(VirtualWallet wallet) {
        return ThreeBizPool.getInstance().virtualWalletBiz.updateWallet(wallet);
    }

    @Override
    public List<VirtualWalletStaticVo> queryStatisWallet() {
        return ThreeBizPool.getInstance().virtualWalletBiz.queryStatisWallet();
    }

    @Override
    public TradeAccountInfoTotalMoney getUserAccountInfo(Integer userId) {
        return ThreeBizPool.getInstance().virtualWalletBiz.getUserAccountInfo(userId);
    }
}
