package com.otc.api.console.ctrl.announcement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

public class AnnouncementSaveReq extends WebApiBaseReq {
    /**
     * 标题，对应表字段为：t_announcement.title
     */
    private String title;

    /**
     * 内容，对应表字段为：t_announcement.content
     */
    private String content;

    /**
     * 推荐
     */
    private Boolean ding;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDing() {
        return ding;
    }

    public void setDing(Boolean ding) {
        this.ding = ding;
    }
}