package com.otc.api.console.ctrl.system.process.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-5-8.
 */
public class ProcessReq extends WebApiBaseReq {

    private Long roleId1;

    private Long roleId2;

    private Long roleId3;

    private Boolean isNeedPwd;

    public Long getRoleId1() {
        return roleId1;
    }

    public void setRoleId1(Long roleId1) {
        this.roleId1 = roleId1;
    }

    public Long getRoleId2() {
        return roleId2;
    }

    public void setRoleId2(Long roleId2) {
        this.roleId2 = roleId2;
    }

    public Long getRoleId3() {
        return roleId3;
    }

    public void setRoleId3(Long roleId3) {
        this.roleId3 = roleId3;
    }

    public Boolean getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(Boolean isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }
}
