package com.otc.api.console.ctrl.announcement.response;

import com.otc.common.api.utils.HTMLSpirit;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by zygong on 17-6-26.
 */
public class AnnouncementPageListResp {
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        String retf = "";
        if (StringUtils.isNotBlank(content)) {
            content = HTMLSpirit.delHTMLTag(content);
            if (content.length() < 45) {
                retf = content + "...";
            } else {
                retf = content.substring(0, 45) + "...";
            }
        }

        return retf;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getDing() {
        return isDing;
    }

    public void setDing(Boolean ding) {
        isDing = ding;
    }
}
