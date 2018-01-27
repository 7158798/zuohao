package com.otc.service.trade.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.trade.pojo.po.TradeJudge;
import com.otc.facade.trade.pojo.vo.TradeJudgeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeJudgeMapper extends BaseMapper<TradeJudge,TradeJudgeVo> {

    List<TradeJudge> selectByCondition(@Param("tradeId") Long tradeId);
}