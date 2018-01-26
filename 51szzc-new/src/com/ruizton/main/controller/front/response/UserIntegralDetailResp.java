package com.ruizton.main.controller.front.response;

import java.io.Serializable;

/**
 * Created by a123 on 17-3-8.
 */
public class UserIntegralDetailResp implements Serializable{
    private String createTime;
    private String integral;
    private String operate;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
