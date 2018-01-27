package com.otc.api.web.ctrl.Announcement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 首页请求数量
 * Created by zygong on 17-4-28.
 */
public class AnnouncementIndexListReq extends WebApiBaseReq {
    /**
     * 获取数量
     */
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
