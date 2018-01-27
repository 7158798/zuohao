package com.otc.facade.message.pojo.cond;

import java.io.Serializable;

/**
 * Created by lx on 17-5-10.
 */
public class MessageCond implements Serializable {

    private String type;

    private Long relationId;

    private Long receive;
    // 是否已读
    private Boolean isRead;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getReceive() {
        return receive;
    }

    public void setReceive(Long receive) {
        this.receive = receive;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
