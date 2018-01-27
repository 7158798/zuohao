package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.RoleResource;
import com.otc.facade.sys.pojo.vo.RoleResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceMapper extends BaseMapper<RoleResource,RoleResourceVo> {

    int saveList(@Param("list")List<RoleResource> roleResourceList);

    int deleteByRoleIdAndResource(@Param("roleId")Long roleId,@Param("resourceIds")List<Long> resourceIds);

    RoleResource queryByRoleIdAndResourceId(@Param("roleId")Long roleId,@Param("resourceId")Long resourceId);

    int deleteByRoleId(@Param("roleId")Long roleId);

    List<RoleResource> queryRoleResourceByRoleId(@Param("roleId")Long roleId);

    List<RoleResource> queryRoleResourceByEmpId(@Param("roleId")Long employeeId);
}