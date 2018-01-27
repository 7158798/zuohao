package com.szzc.common.utils.lsk.resp;

import java.math.BigDecimal;

/**
 * Created by lx on 17-5-17.
 */
public class BalanceResp extends BaseJson {

    private BigDecimal balance;
    private BigDecimal unconfirmedBalance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(BigDecimal unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }
}
