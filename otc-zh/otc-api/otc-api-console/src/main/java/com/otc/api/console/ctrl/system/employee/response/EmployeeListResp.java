/**
 * Creation Date:Sep 29, 201510:30:24 AM
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.otc.api.console.ctrl.system.employee.response;

import com.jucaifu.common.pojo.vo.BaseResp;
import com.otc.facade.sys.enums.EmployeeStatus;
import com.otc.facade.sys.pojo.po.Employee;
import org.apache.commons.lang.StringUtils;

/**
 * 用户列表查询响应bean
 *
 * @author yangyy
 */
public class EmployeeListResp extends BaseResp {

    /**
     * 员工id
     */
    private Long employeeId;
    /**
     * 员工姓名
     */
    private String employeeName;
    private String loginName;
    /**
     * 联系电话
     */
    private String contactNumber;
    /**
     * 启用状态
     */
    private String status;

    private String statusCode;


    public EmployeeListResp(Employee employee) {
        this.employeeId = employee.getId();
        this.employeeName = employee.getName() == null ? "" : employee.getName();
        this.contactNumber = employee.getPhoneNumber() == null ? "" : employee.getPhoneNumber();
        if (StringUtils.isNotEmpty(employee.getStatus())) {
            this.status = EmployeeStatus.getDescByCode(employee.getStatus());
        }
        this.statusCode = employee.getStatus();
        this.loginName = employee.getLoginName();

    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String userName) {
        this.employeeName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
