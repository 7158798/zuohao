package com.otc.api.console.ctrl.system.role.response;

/**
 * Created by lx on 17-4-26.
 */
public class RoleResp {

    private Long roleId;

    private String name;

    private String description;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

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
}
