package com.otc.facade.message.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class Message extends BasePo {

    /**
     * 对应表字段为：t_message.type
     */
    private String type;

    /**
     * 对应表字段为：t_message.content
     */
    private String content;

    /**
     * 对应表字段为：t_message.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_message.create_user
     */
    private Long createUser;

    /**
     * 对应表字段为：t_message.is_read
     */
    private Boolean isRead;

    /**
     * 对应表字段为：t_message.receive
     */
    private Long receive;

    /**
     * 对应表字段为：t_message.relation_id
     */
    private Long relationId;

    /**
     * 消息code
     */
    private String contentCode;

    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Long getReceive() {
        return receive;
    }

    public void setReceive(Long receive) {
        this.receive = receive;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }
}