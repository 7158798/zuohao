package com.szzc.api.app.ctrl.request;

/**
 * Created by a123 on 17-3-23.
 */
public class RechargeCnyInitReq extends AppBaseReq{
    private int type ;  //3 支付宝   4 银行卡

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
