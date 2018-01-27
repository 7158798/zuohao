package com.szzc.fentrustLog.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.fentrustLog.pojo.dto.FentrustDto;
import com.szzc.facade.fentrustLog.pojo.po.Fentrust;
import com.szzc.facade.fentrustLog.pojo.vo.FentrustVo;

import java.util.List;

public interface FentrustMapper extends BaseMapper<Fentrust, FentrustVo> {


    List<FentrustDto> getFentrustByConditionPage(FentrustVo vo);
}