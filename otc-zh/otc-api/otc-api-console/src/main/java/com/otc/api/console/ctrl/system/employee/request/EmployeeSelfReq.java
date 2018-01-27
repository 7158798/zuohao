package com.otc.api.console.ctrl.system.employee.request;


import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by yangyy on 15-12-17.
 */
public class EmployeeSelfReq extends WebApiBaseReq {

    private String phoneNumber;

    private String oldPasswd;

    private String newPasswd;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
