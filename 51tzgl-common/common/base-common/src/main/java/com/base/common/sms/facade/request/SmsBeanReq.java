package com.base.common.sms.facade.request;

import java.io.Serializable;

/**
 * @author luwei
 * @Date 16-9-29 上午10:12
 */
public class SmsBeanReq implements Serializable {

    /**短信发送的手机号**/
    private String phoneNumber;

    /**
     * 获取手机号
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置发送目标手机号
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
