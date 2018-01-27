package com.otc.api.web.ctrl.user.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by fenggq on 17-5-15.
 */
public class SendMailCodeReq extends WebApiBaseReq{
    private String mailType;

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }
}
