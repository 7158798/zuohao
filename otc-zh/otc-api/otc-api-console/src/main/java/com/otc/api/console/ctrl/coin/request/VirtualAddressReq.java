package com.otc.api.console.ctrl.coin.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-6-6.
 */
public class VirtualAddressReq extends WebApiBaseReq {

    // 钱包密码
    private String walletPwd;

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd;
    }
}
