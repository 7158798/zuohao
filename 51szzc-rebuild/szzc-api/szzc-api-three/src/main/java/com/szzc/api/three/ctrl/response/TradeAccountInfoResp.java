package com.szzc.api.three.ctrl.response;

import com.szzc.facade.wallet.pojo.dto.TradeAccountInfoTotalMoney;

/**
 * Created by zygong on 17-7-19.
 */
public class TradeAccountInfoResp {
    private boolean result;

    private TradeAccountInfoTotalMoney body;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public TradeAccountInfoTotalMoney getBody() {
        return body;
    }

    public void setBody(TradeAccountInfoTotalMoney body) {
        this.body = body;
    }
}
