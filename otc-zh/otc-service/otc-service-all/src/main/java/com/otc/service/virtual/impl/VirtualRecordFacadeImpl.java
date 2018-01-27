package com.otc.service.virtual.impl;

import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import com.otc.facade.virtual.service.VirtualRecordFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by lx on 17-4-17.
 */
public class VirtualRecordFacadeImpl implements VirtualRecordFacade {

    @Override
    public VirtualRecordVo queryByConditionPage(VirtualRecordVo vo) {
        return OTCBizPool.getInstance().virtualRecordBiz.queryByConditionPage(vo);
    }

    @Override
    public String queryWithdrawAddress(Long coinId, Long userId) {
        return OTCBizPool.getInstance().virtualRecordBiz.queryWithdrawAddress(coinId, userId);
    }

    @Override
    public void cancelWithdrawByUser(Long id, Long userId) {
        OTCBizPool.getInstance().virtualRecordBiz.cancelWithdrawByUser(id,userId);
    }
}
