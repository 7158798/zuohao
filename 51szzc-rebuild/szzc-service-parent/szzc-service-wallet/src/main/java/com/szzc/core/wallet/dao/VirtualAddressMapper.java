package com.szzc.core.wallet.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.wallet.pojo.cond.VirtualAddressCond;
import com.szzc.facade.wallet.pojo.po.VirtualAddress;
import com.szzc.facade.wallet.pojo.vo.VirtualAddressVo;

import java.util.List;

/**
 * The interface Virtual address mapper.
 */
public interface VirtualAddressMapper extends BaseMapper<VirtualAddress,VirtualAddressVo> {

    /**
     * Query list by condition list.
     *
     * @param cond the cond
     * @return the list
     */
    List<VirtualAddress> queryListByCondition(VirtualAddressCond cond);
}