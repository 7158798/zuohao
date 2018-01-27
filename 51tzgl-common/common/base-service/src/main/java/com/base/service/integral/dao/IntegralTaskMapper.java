package com.base.service.integral.dao;


import com.jucaifu.core.dao.BaseMapper;
import com.base.facade.integral.pojo.po.IntegralTask;
import com.base.facade.integral.pojo.vo.IntegralTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegralTaskMapper extends BaseMapper<IntegralTask, IntegralTaskVo> {

    IntegralTask selectByTaskNo(String taskNo);

    List<IntegralTask> getActivitySignInTask(@Param("signInActivity") List<String> signInActivity);
}