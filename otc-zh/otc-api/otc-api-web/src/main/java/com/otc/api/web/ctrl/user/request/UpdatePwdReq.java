package com.otc.api.web.ctrl.user.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

import java.io.Serializable;

/**
 * Created by yangyy on 15-11-25.
 */
public class UpdatePwdReq extends WebApiBaseReq {

    private String newPassword;

    private String oldPassword;

    private String code;

    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

}
