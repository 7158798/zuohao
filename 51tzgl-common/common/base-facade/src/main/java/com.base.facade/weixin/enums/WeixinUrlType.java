package com.base.facade.weixin.enums;

/**
 * Created by lx on 16-12-23.
 */
public enum WeixinUrlType {

    send_redpack("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack","发送普通红包");

    private String url;

    private String desc;

    WeixinUrlType(String url,String desc){
        this.url = url;
        this.desc = desc;
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
