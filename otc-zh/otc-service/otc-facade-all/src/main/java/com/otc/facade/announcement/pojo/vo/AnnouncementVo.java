package com.otc.facade.announcement.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

public class AnnouncementVo extends BasePageVo {
    /**
     * 标题，对应表字段为：t_announcement.title
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

}