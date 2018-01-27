package com.otc.service.trade.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.trade.pojo.po.Trade;
import com.otc.facade.trade.pojo.vo.TradeVo;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface TradeMapper extends BaseMapper<Trade,TradeVo> {
    /**
     * 综合统计
     * @param dayTime   日期
     * @param status    状态
     * @return
     */
    List<CountVoEx> countTrade(@Param("dayTime")String dayTime, @Param("status") String status);

    /**
     * 后台
     * @param vo
     */
    List<Trade> getConsoleListByConditionPage(TradeVo vo);

    /**
     * 按条件查询交易列表
     * @param vo
     * @return
     */
    List<Trade> getByCondition(TradeVo vo);

    /**
     *
     */
    Trade getLastTrade(@Param("today")Date today);

    /**
     * 取消超时交易
     * @param cancelType
     */
    void cancelForTimeOut(@Param("cancelType") Integer cancelType);

    /**
     * 获取已超时交易
     * @param cancelStatus
     * @return
     */
    List<Trade> getTimeOutList(@Param("cancelStatus") Integer cancelStatus);

    /**
     * 获取未评价交易
     * @return
     */
    List<Trade> getJudgeTimeOutList(@Param("status") Integer status
            ,@Param("endTime") Date endTime,@Param("judgeStatus") Integer judgeStatus );

    /**
     * 取消单条超时交易
     * @param tradeId
     */
    void cancelForTradeId(@Param("tradeId")Long tradeId,@Param("cancelType") Integer cancelType);
}