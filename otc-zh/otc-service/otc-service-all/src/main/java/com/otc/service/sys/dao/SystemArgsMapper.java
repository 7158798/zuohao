package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.SystemArgs;
import com.otc.facade.sys.pojo.vo.SystemArgsVo;

public interface SystemArgsMapper extends BaseMapper<SystemArgs,SystemArgsVo> {

    SystemArgs getSystemArgsByKey(String key);
}
