package com.szzc.api.app.pojo;

import java.io.Serializable;

/**
 * Created by yangyy on 15-11-23.
 */
public class MobileNumberReq implements Serializable {

    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
