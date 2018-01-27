package com.otc.facade.message.pojo.vo;

import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * Created by lx on 17-5-9.
 */
public class MessageVo extends BasePageVo {

    private String type;

    private Long receive;

    private Boolean isRead;

    private Long relationId;

    private String timeStr;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
