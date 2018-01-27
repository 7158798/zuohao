package com.otc.facade.advertisement.service;

import com.otc.facade.advertisement.pojo.po.TransactionDes;

/**
 * Created by zygong on 17-4-27.
 */
public interface TransactionDesFacade {
    TransactionDes getByTypeAndId(Long id, Integer type);
}
