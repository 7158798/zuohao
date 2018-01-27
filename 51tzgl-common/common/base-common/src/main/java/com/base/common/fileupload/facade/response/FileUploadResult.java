package com.base.common.fileupload.facade.response;

/**
 * @author luwei
 * @Date 11/9/16 3:35 PM
 */
public class FileUploadResult {

    /**
     * 绝对地址
     */
    private String absoluteUrl;

    /**
     * 相对地址
     */
    private String relativeUrl;


    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }
}
