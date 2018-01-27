/**
 * Creation Date:Sep 29, 201510:26:09 AM
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.otc.api.console.ctrl.system.employee.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 用户列表查询请求bean
 *
 * @author yangyy
 */
public class EmployeeListReq extends WebApiBaseReq {

    /** 公司id */
    private String companyId;
    /** 部门id */
    private String deptId;
    /** 职位 */
    private String position;
    /** 姓名 */
    private String employeeName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String userName) {
        this.employeeName = userName;
    }

}
