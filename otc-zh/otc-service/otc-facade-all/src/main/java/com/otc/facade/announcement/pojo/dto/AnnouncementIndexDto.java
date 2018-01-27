package com.otc.facade.announcement.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页公告
 * Created by zygong on 17-4-28.
 */
public class AnnouncementIndexDto implements Serializable {
    private Integer id;
    private String title;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonFormat(timezone="GMT+8", pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
