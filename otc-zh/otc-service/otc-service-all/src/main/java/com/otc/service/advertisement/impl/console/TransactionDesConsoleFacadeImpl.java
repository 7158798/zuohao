package com.otc.service.advertisement.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.pojo.vo.TransactionDesVo;
import com.otc.facade.advertisement.service.console.TransactionDesConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.advertisement.impl.TransactionDesFacadeImpl;
import org.springframework.stereotype.Component;

/**
 * Created by zygong on 17-4-27.
 */
@Component
@Service
public class TransactionDesConsoleFacadeImpl extends TransactionDesFacadeImpl implements TransactionDesConsoleFacade {

    @Override
    public boolean save(TransactionDes transactionDes) {
        return OTCBizPool.getInstance().transactionDesBiz.save(transactionDes);
    }

    @Override
    public int update(TransactionDes transactionDes) {
        return OTCBizPool.getInstance().transactionDesBiz.update(transactionDes);
    }

    @Override
    public int delete(Long id) {
        return OTCBizPool.getInstance().transactionDesBiz.delete(id);
    }

    @Override
    public TransactionDesVo queryCountByConditionPage(TransactionDesVo vo) {
        return OTCBizPool.getInstance().transactionDesBiz.queryCountByConditionPage(vo);
    }
}
