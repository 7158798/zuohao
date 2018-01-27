package com.otc.api.web.ctrl.message.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.facade.message.pojo.po.Message;

/**
 * Created by lx on 17-5-10.
 */
public class ChatResp {

    private Long from;

    private String fromName;
    // 头像
    private String avatar;

    private Long to;

    private String content;

    private String createDate;

    private Long id;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void cpoy(Message message){
        this.content = message.getContent();
        this.setCreateDate(DateHelper.date2String(message.getCreateDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        this.from = message.getCreateUser();
        this.to = message.getReceive();
        this.id = message.getId();
    }
}
