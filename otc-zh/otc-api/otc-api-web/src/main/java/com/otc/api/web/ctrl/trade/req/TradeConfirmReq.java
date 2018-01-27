package com.otc.api.web.ctrl.trade.req;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by a123 on 17-5-15.
 */
public class TradeConfirmReq extends WebApiBaseReq{
    private String vrCode;

    public String getVrCode() {
        return vrCode;
    }

    public void setVrCode(String vrCode) {
        this.vrCode = vrCode;
    }
}
