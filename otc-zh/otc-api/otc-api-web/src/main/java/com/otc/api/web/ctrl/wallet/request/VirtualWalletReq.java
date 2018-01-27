package com.otc.api.web.ctrl.wallet.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-4-19.
 */
public class VirtualWalletReq extends WebApiBaseReq {

    private Long coinId;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }
}
