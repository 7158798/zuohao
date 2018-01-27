package com.otc.facade.virtual.service;

import com.otc.facade.virtual.pojo.po.VirtualCoin;
import com.otc.facade.virtual.pojo.po.Withdrawfees;

import java.util.List;
import java.util.Map;

/**
 * Created by lx on 17-4-19.
 */
public interface VirtualCoinFacade {

    VirtualCoin isExist(Long id);

    /**
     * 获取已发布币种
     * @return
     */
    Map<Long,VirtualCoin> getVirtualCoin();
    /**
     * 全部币种（包含未发布）
     * @return
     */
    Map<Long,VirtualCoin> getAllVirtualCoin();

    /**
     * 通过币种id获取手续费配置
     * @param coinId
     * @return
     */
    List<Withdrawfees> getFees(Long coinId);
}
