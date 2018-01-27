package com.szzc.api.app.ctrl.request;

/**
 * 阿里支付请求封装
 * Created by luwei on 17-4-14.
 */
public class AlipayReq extends AppBaseReq{

    private String tradeid;


    public String getTradeid() {
        return tradeid;
    }

    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }
}
