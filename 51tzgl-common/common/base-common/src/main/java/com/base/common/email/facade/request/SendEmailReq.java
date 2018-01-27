package com.base.common.email.facade.request;

import java.io.Serializable;

/**
 * Created by luwei on 17-6-16.
 */
public class SendEmailReq implements Serializable {

    //邮件主题
    private String subject;

    //邮件内容
    private String content;

    //收件地址
    private String toAddress;

    //发件地址
    private String fromAddress;

    public SendEmailReq() {

    }

    public SendEmailReq(String subject, String content, String toAddress, String fromAddress) {
        this.subject = subject;
        this.content = content;
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
