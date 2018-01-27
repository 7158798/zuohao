package com.jucaifu.common.mail;

import java.io.InputStream;

/**
 * MailSendAttachmentInfo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/18.
 */
public class MailSendAttachmentInfo {

    private String name;

    private InputStream inStream;

    /**
     * Instantiates a new Mail send attachment.
     *
     * @param name     the name
     * @param inStream the in stream
     */
    public MailSendAttachmentInfo(String name, InputStream inStream) {
        this.name = name;
        this.inStream = inStream;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets in stream.
     *
     * @return the in stream
     */
    public InputStream getInStream() {
        return inStream;
    }

    /**
     * Sets in stream.
     *
     * @param inStream the in stream
     */
    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }

}
