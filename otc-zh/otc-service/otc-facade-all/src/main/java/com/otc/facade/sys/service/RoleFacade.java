package com.otc.facade.sys.service;

import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.poex.RoleEx;

import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
public interface RoleFacade {

    Integer queryExistsNameByAdd(String roleName);

    Integer queryExistsNameByUpdate(String roleName, Long id);

    void addRole(Role role,List<Resource> resourceList);

    void updateRole(Role role,List<Resource> resourceList);

    void deleteById(Long id);

    RoleEx queryRoleById(Long id);
}
