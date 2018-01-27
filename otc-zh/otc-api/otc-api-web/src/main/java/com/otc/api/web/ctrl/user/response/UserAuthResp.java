package com.otc.api.web.ctrl.user.response;

/**
 * Created by fenggq on 17-5-15.
 */
public class UserAuthResp {
    private String realName;

    private String passportType;

    private String passportNo;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }
}
