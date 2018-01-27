package com.base.facade.info.service;

import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;

/**
 * @author luwei
 * @Date 11/8/16 2:49 PM
 */
public interface VirtualCurrencyFacade {

    /**
     * 分页查询虚拟货币信息
     *
     * @param vo
     * @return
     */
    VirtualCurrencyVo queryCncyPageListByConditionPage(VirtualCurrencyVo vo);

    /**
     * 根据id查询虚拟货币信息
     * @param id
     * @return
     */
    VirtualCurrency select(Long id);


    /**
     * 根据市场代码查询虚拟货币信息
     * @param marketCode
     * @return
     */
    VirtualCurrency selectByMarketCode(String marketCode);

}
