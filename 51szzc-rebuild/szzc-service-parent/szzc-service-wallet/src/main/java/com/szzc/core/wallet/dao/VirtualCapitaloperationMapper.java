package com.szzc.core.wallet.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualCapitaloperationCond;
import com.szzc.facade.wallet.pojo.po.VirtualCapitaloperation;
import com.szzc.facade.wallet.pojo.vo.VirtualCapitaloperationVo;

import java.util.List;

/**
 * The interface Virtual capitaloperation mapper.
 */
public interface VirtualCapitaloperationMapper extends BaseMapper<VirtualCapitaloperation,VirtualCapitaloperationVo> {

    /**
     * Query list by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualCapitaloperation> queryListByCondition(VirtualCapitaloperationCond cond);
}