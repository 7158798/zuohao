package com.jucaifu.common.mail;

import java.util.List;

/**
 * MailSendInfo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/30.
 */
public class MailSendInfo {

    /**
     * 发件人
     **/
    private String from;
    /**
     * 收件人
     **/
    private String to;
    /**
     * 主题
     **/
    private String subject;
    /**
     * 邮件内容
     **/
    private String content;

    /**
     * 接受为多个人
     */
    private String[] toArray = new String[]{};
    /**
     * 抄送为多个人
     */
    private String[] ccArray = new String[]{};
    /**
     * 多个附件列表
     */
    private List<MailSendAttachmentInfo> attachmentList;


    public MailSendInfo() {
    }

    public MailSendInfo(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public MailSendInfo(String subject, String content, String[] toArray, String[] ccArray, List<MailSendAttachmentInfo> attachmentList) {
        this.subject = subject;
        this.content = content;
        this.toArray = toArray;
        this.ccArray = ccArray;
        this.attachmentList = attachmentList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String[] getToArray() {
        return toArray;
    }

    public void setToArray(String[] toArray) {
        this.toArray = toArray;
    }

    public String[] getCcArray() {
        return ccArray;
    }

    public void setCcArray(String[] ccArray) {
        this.ccArray = ccArray;
    }

    public List<MailSendAttachmentInfo> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<MailSendAttachmentInfo> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
