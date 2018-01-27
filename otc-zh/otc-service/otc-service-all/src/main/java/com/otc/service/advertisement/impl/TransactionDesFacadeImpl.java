package com.otc.service.advertisement.impl;

import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.service.TransactionDesFacade;
import com.otc.pool.OTCBizPool;

/**
 * Created by zygong on 17-4-27.
 */
public class TransactionDesFacadeImpl implements TransactionDesFacade {
    @Override
    public TransactionDes getByTypeAndId(Long id, Integer type) {
        return OTCBizPool.getInstance().transactionDesBiz.getByTypeAndId(id, type);
    }
}
