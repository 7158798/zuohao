package com.jucaifu.common.dfs;

import java.io.Serializable;

/**
 * DFSFilePo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/11.
 */
public class DFSFilePo implements Serializable {

    private String host;
    private String fileId;

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets host.
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets file id.
     *
     * @return the file id
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets file id.
     *
     * @param fileId the file id
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * Build full path.
     *
     * @return the string
     */
    public String buildFullPath() {
        return host + fileId;
    }
}
