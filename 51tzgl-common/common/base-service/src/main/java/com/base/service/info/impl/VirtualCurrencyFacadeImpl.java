package com.base.service.info.impl;

import com.base.facade.info.pojo.po.VirtualCurrency;
import com.base.facade.info.pojo.vo.VirtualCurrencyVo;
import com.base.facade.info.service.VirtualCurrencyFacade;
import com.base.service.pool.BaseServiceBizPool;

/**
 * @author luwei
 * @Date 11/8/16 2:59 PM
 */
public class VirtualCurrencyFacadeImpl implements VirtualCurrencyFacade{


    public VirtualCurrencyVo queryCncyPageListByConditionPage(VirtualCurrencyVo vo) {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.queryCncyPageListByConditionPage(vo);
    }

    /**
     * 根据id查询虚拟货币信息
     * @param id
     * @return
     */
    public VirtualCurrency select(Long id) {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.select(id);
    }

    /**
     * 根据市场代码查询虚拟货币信息
     * @param marketCode
     * @return
     */
    public VirtualCurrency selectByMarketCode(String marketCode) {
        return BaseServiceBizPool.getInstance().virtualCurrencyBiz.selectByMarketCode(marketCode);
    }
}
