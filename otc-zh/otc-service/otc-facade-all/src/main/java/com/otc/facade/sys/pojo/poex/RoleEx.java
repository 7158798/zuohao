package com.otc.facade.sys.pojo.poex;

import com.otc.facade.sys.pojo.po.Role;
import com.otc.facade.sys.pojo.po.RoleResource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 17-4-26.
 */
public class RoleEx implements Serializable {

    private Role role;

    private List<RoleResource> resourceList;

    public List<RoleResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<RoleResource> resourceList) {
        this.resourceList = resourceList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
