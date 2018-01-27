package com.szzc.facade.wallet.service;

import com.szzc.facade.wallet.pojo.cond.VirtualWalletCond;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;

import java.util.List;

/**
 * Created by zygong on 17-7-19.
 */
public interface WalletFacade {

    VirtualWallet queryWalletByCoinAndUser(Integer userId, Integer coinId);

    List<VirtualWallet> queryListByCondition(VirtualWalletCond cond);

    int updateWallet(VirtualWallet wallet);


    /**
     * 汇总统计全平台账户可用、冻结、余额（可用+冻结）
     * @return
     */
    List<VirtualWalletStaticVo> queryStatisWallet();

}
