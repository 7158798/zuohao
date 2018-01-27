package com.otc.service.trade.impl.console;

import com.alibaba.dubbo.config.annotation.Service;
import com.otc.facade.base.CountVo;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.trade.service.console.TradeConsoleFacade;
import com.otc.pool.OTCBizPool;
import com.otc.service.trade.impl.TradeFacadeImpl;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
@Service
public class TradeConsoleFacadeImpl extends TradeFacadeImpl implements TradeConsoleFacade{
    @Override
    public void cancelTradeByAdmin(Long employeeid, Long tradeId, String remark,String confirmPwd) {
        OTCBizPool.getInstance().tradeBiz.cancelTradeByAdmin(employeeid,tradeId,remark,confirmPwd);
    }

    @Override
    public void adminConfirm(Long employeeId, Long tradeId, String remark,String confirmPwd) {
        OTCBizPool.getInstance().tradeBiz.adminConfirm(employeeId,tradeId,remark,confirmPwd);
    }

    @Override
    public void adminConfirmForRun(Long employeeId, Long tradeId, String remark, String confirmPwd) {
        OTCBizPool.getInstance().tradeBiz.adminConfirmForRun(employeeId,tradeId,remark,confirmPwd);
    }

    @Override
    public List<CountVoEx> countTrade() {
        return OTCBizPool.getInstance().tradeBiz.countTrade();
    }

    @Override
    public List<CountVoEx> countTrade(Date dayTime, String status) {
        return OTCBizPool.getInstance().tradeBiz.countTrade(dayTime, status);
    }

    @Override
    public TradeVo queryListByConditionPage(TradeVo vo) {
        List<Trade> list = OTCBizPool.getInstance().tradeBiz.getConsoleListByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }
}
