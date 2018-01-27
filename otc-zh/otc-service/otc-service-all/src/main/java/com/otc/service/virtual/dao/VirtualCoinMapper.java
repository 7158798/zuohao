package com.otc.service.virtual.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.virtual.pojo.cond.VirtualCoinCond;
import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.vo.VirtualCoinVo;

import java.util.List;

/**
 * The interface Virtual coin mapper.
 */
public interface VirtualCoinMapper extends BaseMapper<VirtualCoin,VirtualCoinVo> {

    /**
     * Query count by condition page list.
     *
     * @param vo the vo
     * @return the list
     */
    List<VirtualCoin> queryCountByConditionPage(VirtualCoinVo vo);


    /**
     * Query list by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualCoin> queryListByCondition(VirtualCoinCond cond);

    /**
     * Query list by shortName.
     * @param coin
     * @return
     */
    List<VirtualCoin> queryListByShortName(VirtualCoin coin);
}