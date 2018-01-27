package com.szzc.wallet.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualWalletCond;
import com.szzc.facade.wallet.pojo.po.VirtualWallet;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletStaticVo;
import com.szzc.facade.wallet.pojo.vo.VirtualWalletVo;

import java.util.List;

/**
 * The interface Virtual wallet mapper.
 */
public interface VirtualWalletMapper extends BaseMapper<VirtualWallet,VirtualWalletVo> {

    /**
     * Query wallet by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualWallet> queryWalletByCondition(VirtualWalletCond cond);

    /**
     * Update wallet int.
     *
     * @param wallet the wallet
     * @return the int
     */
    int updateWallet(VirtualWallet wallet);

    /**
     * 汇总统计全平台账户可用、冻结、余额（可用+冻结）
     * @return
     */
    List<VirtualWalletStaticVo> queryStatisWallet();
}