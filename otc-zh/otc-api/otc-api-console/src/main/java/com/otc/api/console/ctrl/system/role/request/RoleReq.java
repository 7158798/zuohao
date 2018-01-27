package com.otc.api.console.ctrl.system.role.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.util.List;

/**
 * Created by lx on 17-4-27.
 */
public class RoleReq extends WebApiBaseReq {

    private Long roleId;

    private String name;

    private String description;
    // 资源id集合
    public List<Long> resourceIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
