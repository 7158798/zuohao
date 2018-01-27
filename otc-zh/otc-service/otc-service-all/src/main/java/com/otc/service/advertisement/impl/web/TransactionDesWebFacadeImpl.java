package com.otc.service.advertisement.impl.web;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.service.TransactionDesFacade;
import com.otc.facade.advertisement.service.web.TransactionDesWebFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.TransactionDesFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zygong on 17-4-27.
 */
@Component
@Service
public class TransactionDesWebFacadeImpl extends TransactionDesFacadeImpl implements TransactionDesWebFacade {
}
