package com.ruizton.main.controller.front.response;

import java.io.Serializable;

/**
 * Created by a123 on 17-3-8.
 */
public class IntrolResp implements Serializable{
    private String logName;

    private String createTime;

    private int integral;

    private String isRealValid;


    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getIsRealValid() {
        return isRealValid;
    }

    public void setIsRealValid(String isRealValid) {
        this.isRealValid = isRealValid;
    }
}
