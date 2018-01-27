package com.otc.service.virtual.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.base.CountVo;
import com.otc.facade.base.CountVoEx;
import com.otc.facade.virtual.pojo.cond.VirtualRecordCond;
import com.otc.facade.virtual.pojo.po.VirtualRecord;
import com.otc.facade.virtual.pojo.poex.VirtualRecordEx;
import com.otc.facade.virtual.pojo.vo.VirtualRecordVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface VirtualRecordMapper extends BaseMapper<VirtualRecord,VirtualRecordVo> {

    List<VirtualRecord> queryByConditionPage(VirtualRecordVo vo);

    List<VirtualRecord> queryListVirtualRecord(VirtualRecordCond cond);

    List<VirtualRecordEx> queryConsoleByConditionPage(VirtualRecordVo vo);

    // 综合统计
    List<CountVoEx> countVitralRecord(@Param("dayTime") String dayTime, @Param("status") String status, @Param("type") String type);
}