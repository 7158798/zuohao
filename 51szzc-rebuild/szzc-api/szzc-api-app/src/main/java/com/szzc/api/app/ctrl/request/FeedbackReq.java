package com.szzc.api.app.ctrl.request;

/**
 * app 问题反馈接收对象
 * Created by zygong on 17-3-28.
 */
public class FeedbackReq extends AppBaseReq{
    private String description;
    private Integer type;

    public String getDescription() {
        return description != null ? description : null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type != null ? type : null;
    }

    public void setType(int type) {
        this.type = type;
    }
}
