package com.base.common.push.facade.messsage;

import java.util.Date;

/**
 * 消息bean
 * Created by liuxun on 16-9-26.
 */
public class PushMessage {
    // 通用字段
    // 平台
    private Integer targetPlatform;
    // 消息标题
    private String title;
    // 消息内容
    private String content;
    // 定时发送的时间
    private Date sendDate;
    // 设备号，用于单个推送
    private String deviceId;

    public Integer getTargetPlatform() {
        return targetPlatform;
    }

    public void setTargetPlatform(Integer targetPlatform) {
        this.targetPlatform = targetPlatform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
