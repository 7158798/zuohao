package com.otc.service.trade.biz;

import com.jucaifu.common.exceptions.biz.BizException;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import com.otc.facade.trade.enums.TradeOperationEnum;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.po.TradeLog;
import com.otc.facade.trade.pojo.vo.TradeLogVo;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.service.trade.dao.TradeLogMapper;
import com.otc.service.trade.dao.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by fenggq on 17-5-10.
 */
@Service
public class TradeLogBiz extends AbsBaseBiz<TradeLog,TradeLogVo,TradeLogMapper> {
    @Autowired
    private TradeLogMapper tradeLogMapper;
    @Override
    public TradeLogMapper getDefaultMapper() {
        return tradeLogMapper;
    }


    /**
     * 增加交易日志
     * @param UserId  操作用户id
     * @param tradeId  交易id
     * @param beforeStatus  操作前状态
     * @param afterStatus  操作后状态
     * @param tradeOperationEnum  操作类型
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    protected void addLog(Long UserId,Long tradeId,Integer beforeStatus,Integer afterStatus,TradeOperationEnum tradeOperationEnum){
        LOG.dStart(this,"增加交易日志");
        if(tradeId == null){
            throw new BizException("交易id为空");
        }
        TradeLog log = new TradeLog();
        log.setUserId(UserId);
        log.setBeforeStatus(beforeStatus);
        log.setAfterStatus(afterStatus);
        log.setCreatetime(new Date());
        log.setTradeId(tradeId);
        log.setOperationType(TradeOperationEnum.USER_OPERATION.getCode());
        log.setCreatetime(new Date());
        this.insert(log);
        LOG.dEnd(this,"增加交易日志");
    }
}

