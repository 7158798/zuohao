package com.szzc.facade.virtualCoin.service;

import java.util.Map;

/**
 * Created by zygong on 17-7-21.
 */
public interface VirtualCoinFacade {
    /**
     * 获取币种简称和对应法币id
     * @return
     */
    Map<String, Long> getActionCoinShortNameAndFtId();
}
