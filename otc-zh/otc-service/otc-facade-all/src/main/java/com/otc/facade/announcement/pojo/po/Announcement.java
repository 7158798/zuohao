package com.otc.facade.announcement.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class Announcement extends BasePo implements Serializable {

    /**
     * 标题，对应表字段为：t_announcement.title
     */
    private String title;

    /**
     * 内容，对应表字段为：t_announcement.content
     */
    private String content;

    /**
     * 最新更新时间，对应表字段为：t_announcement.last_update_time
     */
    private Date lastUpdateTime;

    /**
     * 推荐（0、未推荐   1、推荐），对应表字段为：t_announcement.is_ding
     */
    private Boolean isDing;

    private static final long serialVersionUID = 1L;

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
        this.content = content == null ? null : content.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getIsDing() {
        return isDing;
    }

    public void setIsDing(Boolean isDing) {
        this.isDing = isDing;
    }
}