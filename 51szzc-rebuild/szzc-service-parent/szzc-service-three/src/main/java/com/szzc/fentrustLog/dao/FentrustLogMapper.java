package com.szzc.fentrustLog.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.fentrustLog.pojo.dto.TradeVo;
import com.szzc.facade.fentrustLog.pojo.po.FentrustLog;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zygong on 17-5-26.
 */
public interface FentrustLogMapper extends BaseMapper<FentrustLog,FentrustLogVo> {

    List<TradeVo> tradeOther(@Param("ftrademapping") Long ftrademapping, @Param("number") Long number);
}
