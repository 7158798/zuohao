package com.ruizton.main.controller.front.response;

import java.io.Serializable;

/**
 * Created by a123 on 17-3-7.
 */
public class FintegralActivityResp implements Serializable{
    private String integralType;
    private int integral;
    private String remark;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntegralType() {
        return integralType;
    }

    public void setIntegralType(String integralType) {
        this.integralType = integralType;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
