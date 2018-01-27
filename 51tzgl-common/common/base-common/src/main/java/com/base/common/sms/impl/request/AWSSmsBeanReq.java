package com.base.common.sms.impl.request;

import com.base.common.sms.facade.request.SmsBeanReq;

/**
 * Created by luwei on 17-6-21.
 */
public class AWSSmsBeanReq extends SmsBeanReq {

    /**发送内容**/
    private String content;

    /**短信主题， 该字段目前没有用，没有效果**/
    private String subject;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
