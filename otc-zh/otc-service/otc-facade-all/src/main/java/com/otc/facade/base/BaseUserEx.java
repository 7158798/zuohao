package com.otc.facade.base;

import java.io.Serializable;

/**
 * Created by lx on 17-5-2.
 */
public class BaseUserEx implements Serializable{

    private String emailAddress;
    private String realName;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
