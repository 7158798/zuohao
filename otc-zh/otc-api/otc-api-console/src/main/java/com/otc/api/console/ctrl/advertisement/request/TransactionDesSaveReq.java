package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 交易说明保存
 * Created by zygong on 17-4-27.
 */
public class TransactionDesSaveReq extends WebApiBaseReq {
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
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
