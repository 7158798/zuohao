package com.otc.facade.trade.service.console;

import com.otc.facade.base.CountVo;
import com.otc.facade.trade.pojo.vo.TradeVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.trade.service.TradeFacade;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by fenggq on 17-5-11.
 */
public interface TradeConsoleFacade extends TradeFacade{

    /**
     * 后台取消交易
     * @param employeeid
     * @param tradeId
     * @param remark
     */
    void cancelTradeByAdmin(Long employeeid, Long tradeId,String remark,String confirmPwd);


    /**
     * 后台确认(申诉中) --- 完成交易
     * @param employeeId
     * @param tradeId
     * @param remark
     */
    void adminConfirm(Long employeeId, Long tradeId,String remark,String confirmPwd);


    /**
     * 后台确认(进行中) --- 完成交易
     * @param employeeId
     * @param tradeId
     * @param remark
     */
    void adminConfirmForRun(Long employeeId, Long tradeId,String remark,String confirmPwd);

    /**
     * 综合统计
     * @return
     */
    List<CountVoEx> countTrade();

    /**
     * 综合统计
     * @param dayTime  日期
     * @param status    状态
     * @return
     */
    List<CountVoEx> countTrade(Date dayTime, String status);


    /**
     * 后台查询交易列表
     * @param vo
     * @return
     */
    TradeVo queryListByConditionPage(TradeVo vo);
}
