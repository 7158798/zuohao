package com.szzc.core.wallet.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualCoinTypeCond;
import com.szzc.facade.wallet.pojo.po.VirtualCoinType;
import com.szzc.facade.wallet.pojo.vo.VirtualCoinTypeVo;

import java.util.List;

/**
 * The interface Virtual coin type mapper.
 */
public interface VirtualCoinTypeMapper extends BaseMapper<VirtualCoinType,VirtualCoinTypeVo> {

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
    List<VirtualCoinType> getActiveCoin();
}