package com.otc.facade.advertisement.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class TransactionDes extends BasePo implements Serializable {

    /**
     * 交易类型，对应表字段为：t_transaction_des.type
     */
    private Integer type;

    /**
     * 标题，对应表字段为：t_transaction_des.title
     */
    private String title;

    /**
     * 内容，对应表字段为：t_transaction_des.content
     */
    private String content;

    /**
     * 最新更新时间，对应表字段为：t_transaction_des.last_update_time
     */
    private Date lastUpdateTime;

    /**
         * 删除（0、未删除   1、已删除）
     */
    private Boolean isDelete;

    private static final long serialVersionUID = 1L;
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

}