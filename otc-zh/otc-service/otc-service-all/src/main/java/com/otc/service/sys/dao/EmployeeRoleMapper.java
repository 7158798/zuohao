package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.cond.EmployeeRoleCond;
import com.otc.facade.sys.pojo.po.EmployeeRole;
import com.otc.facade.sys.pojo.vo.EmployeeRoleVo;

import java.util.List;

public interface EmployeeRoleMapper extends BaseMapper<EmployeeRole,EmployeeRoleVo> {

    List<EmployeeRole> queryByCondition(EmployeeRoleCond cond);


}