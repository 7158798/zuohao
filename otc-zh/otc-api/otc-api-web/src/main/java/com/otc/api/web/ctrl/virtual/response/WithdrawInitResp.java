package com.otc.api.web.ctrl.virtual.response;


/**
 * Created by lx on 17-4-25.
 */
public class WithdrawInitResp {
    // 可用余额
    private String total;
    // 提现地址
    private String withdrawAddress;
    // 提现说明
    private String withdrawDes;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWithdrawAddress() {
        return withdrawAddress;
    }

    public void setWithdrawAddress(String withdrawAddress) {
        this.withdrawAddress = withdrawAddress;
    }

    public String getWithdrawDes() {
        return withdrawDes;
    }

    public void setWithdrawDes(String withdrawDes) {
        this.withdrawDes = withdrawDes;
    }
}
