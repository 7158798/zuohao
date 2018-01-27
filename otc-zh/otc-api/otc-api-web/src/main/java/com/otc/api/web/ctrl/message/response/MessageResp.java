package com.otc.api.web.ctrl.message.response;

import com.jucaifu.common.util.DateHelper;
import com.otc.facade.message.enums.MessageType;
import com.otc.facade.message.pojo.po.Message;

/**
 * Created by lx on 17-5-9.
 */
public class MessageResp {

    private String type;

    private String content;

    private Long relationId;

    private String receiveDate;

    private String isRead;

    private String typeCode;

    private Long id;

    private String contentCode;

    private String sendUserName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public void copy(Message message){
        this.id = message.getId();
        this.typeCode = message.getType();
        this.relationId = message.getRelationId();
        this.content = message.getContent();
        this.receiveDate = DateHelper.date2String(message.getCreateDate(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond);
        this.isRead = message.getIsRead()?"已读":"未读";
        this.type = MessageType.typeMap.get(typeCode).getDesc();
        this.contentCode = message.getContentCode();
    }
}
