package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by zygong on 17-4-27.
 */
public class AdvertisementCloseReq extends WebApiBaseReq {
    /**
     * 状态（1、已发布 2、已关闭）
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
