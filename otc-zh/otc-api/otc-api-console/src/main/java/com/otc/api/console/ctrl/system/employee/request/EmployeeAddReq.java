package com.otc.api.console.ctrl.system.employee.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by zhaiyz on 15-12-3.
 */
public class EmployeeAddReq extends WebApiBaseReq {

    private String employeeName;

    private String loginName;

    private String password;

    private String contactNumber;

    private String status;


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
