package com.otc.facade.virtual.service;

import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;

/**
 * Created by lx on 17-4-17.
 */
public interface VirtualRecordFacade {

    VirtualRecordVo queryByConditionPage(VirtualRecordVo vo);

    /**
     * 查询提现地址
     * @param coin
     * @param userId
     * @return
     */
    String queryWithdrawAddress(Long coin,Long userId);

    /**
     * 取消提现
     * @param id
     * @param userId
     */
    void cancelWithdrawByUser(Long id,Long userId);
}
