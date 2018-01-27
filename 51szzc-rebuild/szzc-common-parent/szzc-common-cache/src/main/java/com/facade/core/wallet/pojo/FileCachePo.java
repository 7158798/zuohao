package com.facade.core.wallet.pojo;

import java.io.Serializable;

/**
 * FileCachePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/18.
 */
public class FileCachePo implements Serializable {

    private String fileId;
    private String fileHtmlStr;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileHtmlStr() {
        return fileHtmlStr;
    }

    public void setFileHtmlStr(String fileHtmlStr) {
        this.fileHtmlStr = fileHtmlStr;
    }
    
}
