package com.ruizton.util.antshare.resp;

import java.math.BigDecimal;

/**
 * Created by lx on 17-4-6.
 */
public class BalanceResp extends BaseJson<BalanceResp.Balance> {


    public class Balance {
        // 具体的余额
        private BigDecimal balance;

        private BigDecimal confirmed;


        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public BigDecimal getConfirmed() {
            return confirmed;
        }

        public void setConfirmed(BigDecimal confirmed) {
            this.confirmed = confirmed;
        }
    }
}
