package com.ruizton.main.model.vo;

import java.io.Serializable;

/**
 * Created by zygong on 17-2-28.
 */
public class FvideoVo implements Serializable{

    private int fid;
    private int fTypeId;
    private String fTitle;
    private String fDescription;
    private String fPictureUrl;
    private String fVideoUrl;
    private int fPriority;

    public FvideoVo() {
    }

    public FvideoVo(int fid, int fTypeId, String fTitle, String fDescription, String fPictureUrl, String fVideoUrl, int fPriority) {
        this.fid = fid;
        this.fTypeId = fTypeId;
        this.fTitle = fTitle;
        this.fDescription = fDescription;
        this.fPictureUrl = fPictureUrl;
        this.fVideoUrl = fVideoUrl;
        this.fPriority = fPriority;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getfTypeId() {
        return fTypeId;
    }

    public void setfTypeId(int fTypeId) {
        this.fTypeId = fTypeId;
    }

    public String getfTitle() {
        return fTitle;
    }

    public void setfTitle(String fTitle) {
        this.fTitle = fTitle;
    }

    public String getfDescription() {
        return fDescription;
    }

    public void setfDescription(String fDescription) {
        this.fDescription = fDescription;
    }

    public String getfPictureUrl() {
        return fPictureUrl;
    }

    public void setfPictureUrl(String fPictureUrl) {
        this.fPictureUrl = fPictureUrl;
    }

    public String getfVideoUrl() {
        return fVideoUrl;
    }

    public void setfVideoUrl(String fVideoUrl) {
        this.fVideoUrl = fVideoUrl;
    }

    public int getfPriority() {
        return fPriority;
    }

    public void setfPriority(int fPriority) {
        this.fPriority = fPriority;
    }
}
