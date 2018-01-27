package com.otc.api.console.ctrl.system.employee.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by yangyy on 15-12-8.
 */
public class EmployeeHandleReq extends WebApiBaseReq {

    /** 用户id */
    private Long employeeId;
    /** 状态 */
    private String status;

    private String newPasswd;

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long userId) {
        this.employeeId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
