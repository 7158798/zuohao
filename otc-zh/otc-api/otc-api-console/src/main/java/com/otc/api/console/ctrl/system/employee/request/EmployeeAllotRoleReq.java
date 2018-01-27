/**
 * Creation Date:Oct 12, 20157:44:21 PM
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.otc.api.console.ctrl.system.employee.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.util.ArrayList;
import java.util.List;

/**
 * 给员工添加角色请求参数
 *
 * @author yangyy
 */
public class EmployeeAllotRoleReq extends WebApiBaseReq {

    private Long employeeId;

    private List<Long> roleIds = new ArrayList<>();

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
