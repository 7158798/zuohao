package com.otc.api.console.ctrl.coin.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-4-24.
 */
public class VirtualCoinCutReq extends WebApiBaseReq {

    private Long coinId;

    private String status;
    // 钱包密码
    private String walletPwd;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd;
    }
}
