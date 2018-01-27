package com.otc.core.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zygong on 17-3-23.
 */

public class FquotesPo implements Serializable{
    /**
     * 平台
     */
    private String fplatform;
    /**
     * 人民币
     */
    private String frmb;
    /**
     * 美元
     */
    private String fdollar;
    /**
     * 比率
     */
    private String frate;
    /**
     * 上升/下降
     */
    private String fupordown;
    /**
     * 时间
     */
    private Date fcreatetime;

    public String getFplatform() {
        return fplatform;
    }

    public void setFplatform(String fplatform) {
        this.fplatform = fplatform;
    }

    public String getFrmb() {
        return frmb;
    }

    public void setFrmb(String frmb) {
        this.frmb = frmb;
    }

    public String getFdollar() {
        return fdollar;
    }

    public void setFdollar(String fdollar) {
        this.fdollar = fdollar;
    }

    public String getFrate() {
        return frate;
    }

    public void setFrate(String frate) {
        this.frate = frate;
    }

    public String getFupordown() {
        return fupordown;
    }

    public void setFupordown(String fupordown) {
        this.fupordown = fupordown;
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }
}
