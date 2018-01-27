package com.szzc.fentrustLog.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.fentrustLog.pojo.po.CoinShorNameAndFtrademappingId;
import com.szzc.facade.fentrustLog.pojo.vo.VirtualCoinTypeVo;
import com.szzc.facade.virtualCoin.pojo.po.VirtualCoinType;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;

import java.util.List;

/**
 * The interface Virtual coin type mapper.
 */
public interface VirtualCoinTypeMapper extends BaseMapper<CoinShorNameAndFtrademappingId,VirtualCoinTypeVo> {

    /**
     * 获取可用虚拟币
     * @return
     */
    List<CoinShorNameAndFtrademappingId> getActiveCoin();

    /**
     * Query coin type by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualCoinType> queryCoinTypeByCondition(VirtualCoinTypeCond cond);

    /**
     * 获取可用虚拟币
     * @return
     */
    List<VirtualCoinType> getActiveCoinDetail();
}