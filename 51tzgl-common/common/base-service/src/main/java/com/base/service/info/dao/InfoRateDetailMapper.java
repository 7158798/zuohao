package com.base.service.info.dao;

import com.base.facade.info.pojo.po.InfoRateDetail;
import com.base.facade.info.pojo.poex.InfoRateDetailEx;
import com.base.facade.info.pojo.vo.InfoRateDetailVo;
import com.jucaifu.core.dao.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoRateDetailMapper extends BaseMapper<InfoRateDetail,InfoRateDetailVo> {
    List<InfoRateDetailEx> selectByRateId(@Param("rateId") String rateId);
}
