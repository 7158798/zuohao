package com.otc.api.console.ctrl.virtual.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * Created by lx on 17-5-8.
 */
public class RecordAuditReq extends WebApiBaseReq {

    private String confirmPwd;

    private String walletPwd;

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getWalletPwd() {
        return walletPwd;
    }

    public void setWalletPwd(String walletPwd) {
        this.walletPwd = walletPwd;
    }
}
