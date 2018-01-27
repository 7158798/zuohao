package com.otc.core.validator;

/**
 * Created by zhaiyz on 15-11-20.
 */
public class SmsCodeGet {

    /**
     * 状态，true生成成功，false生成失败
     */
    private boolean status;

    /**
     * 内容，status为true时，为动态码，status为false时，为错误原因
     */
    private String content;

    public SmsCodeGet(boolean status, String content) {
        this.status = status;
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
