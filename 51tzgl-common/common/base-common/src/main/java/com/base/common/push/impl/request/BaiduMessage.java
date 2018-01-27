package com.base.common.push.impl.request;

import com.base.common.push.facade.messsage.PushMessage;

/**
 * 推送业务Bean
 * Created by liuxun on 16-9-29.
 */
public class BaiduMessage extends PushMessage {
    // 消息ID
    private String messageId;
    // 产品ID
    private String productId;
    // 类型
    private Integer contentType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }
}
