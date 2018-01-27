package com.otc.service.sys.impl;

import com.otc.facade.sys.pojo.po.Resource;
import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.poex.RoleEx;
import com.otc.facade.sys.service.RoleFacade;
import com.otc.pool.OTCBizPool;

import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
public class RoleFacadeImpl implements RoleFacade {

    @Override
    public Integer queryExistsNameByAdd(String roleName) {
        return null;
    }

    @Override
    public Integer queryExistsNameByUpdate(String roleName, Long id) {
        return null;
    }

    @Override
    public void addRole(Role role,List<Resource> resourceList) {
        OTCBizPool.getInstance().roleBiz.addRole(role, resourceList);
    }

    @Override
    public void updateRole(Role role,List<Resource> resourceList) {
        OTCBizPool.getInstance().roleBiz.updateRole(role, resourceList);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public RoleEx queryRoleById(Long id) {
        return OTCBizPool.getInstance().roleBiz.queryRoleById(id);
    }
}
