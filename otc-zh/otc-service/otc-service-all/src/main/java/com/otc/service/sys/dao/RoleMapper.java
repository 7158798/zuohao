package com.otc.service.sys.dao;

import com.jucaifu.core.dao.BaseMapper;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role,RoleVo> {

    /**
     * 分页查询角色信息
     * @param vo
     * @return
     */
    List<Role> queryRolePageListByConditionPage(RoleVo vo);

    Role selectById(Long id);

    void deleteById(Long id);

    String queryMaxRoleCode();

    Integer queryExistsNameByAdd(String roleName);

    Integer queryExistsNameByUpdate(@Param("roleName") String roleName, @Param("id") Long id);

    List<Role> selectAllRole();
}