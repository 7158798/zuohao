package com.ruizton.main.model.vo;

/**
 * Created by zygong on 17-3-17.
 */
public class HotHelpVo {
    private int ftypeid;
    private String ftype;
    private String ftitle;
    private int fid;

    public HotHelpVo() {
    }

    public HotHelpVo(String ftype, String ftitle, int fid, int ftypeid) {
        this.ftype = ftype;
        this.ftitle = ftitle;
        this.fid = fid;
        this.ftypeid = ftypeid;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFtypeid() {
        return ftypeid;
    }

    public void setFtypeid(int ftypeid) {
        this.ftypeid = ftypeid;
    }
}
