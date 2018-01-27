package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 保存
 * Created by zygong on 17-4-27.
 */
public class TransactionDesDetailReq extends WebApiBaseReq {
    /**
     * 类型
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
