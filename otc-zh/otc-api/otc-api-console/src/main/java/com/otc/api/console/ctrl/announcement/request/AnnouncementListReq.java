package com.otc.api.console.ctrl.announcement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 公告列表
 * Created by zygong on 17-4-28.
 */
public class AnnouncementListReq extends WebApiBaseReq {
    /**
     * 标题
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
