package com.szzc.api.three.ctrl.response.szzc;

/**
 * Created by lx on 17-7-22.
 */
public class SzzcBaseResp {

    private Integer resultCode;
    private String msg;
    // szzc 委托订单id
    private String orderId;

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
