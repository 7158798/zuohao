package com.otc.facade.advertisement.service.console;

import com.otc.facade.advertisement.pojo.po.TransactionDes;
import com.otc.facade.advertisement.pojo.vo.AdvertisementVo;
import com.otc.facade.advertisement.pojo.vo.TransactionDesVo;
import com.otc.facade.advertisement.service.TransactionDesFacade;

/**
 * Created by zygong on 17-4-27.
 */
public interface TransactionDesConsoleFacade extends TransactionDesFacade {
    TransactionDesVo queryCountByConditionPage(TransactionDesVo vo);
    boolean save(TransactionDes transactionDes);
    int update(TransactionDes transactionDes);
    int delete(Long id);

}
