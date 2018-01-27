package com.szzc.api.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.szzc.facade.api.pojo.po.UserApi;
import com.szzc.facade.api.pojo.vo.UserApiVo;
import org.apache.ibatis.annotations.Param;

public interface UserApiMapper extends BaseMapper<UserApi,UserApiVo> {

    UserApi getUserApiByKey(@Param("apiKey")String apiKey);
}