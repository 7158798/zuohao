package com.base.facade.integral.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

/**
 * Created by yangyy on 16-4-29.
 */
public class UserIntegralOrdersVo extends BasePageVo {

    private String mobileNumber;

    private String niceName;

    private Date startTime;

    private Date endTime;

    private String userRole;

    private String status;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
