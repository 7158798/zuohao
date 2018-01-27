package com.szzc.common.utils.lsk.resp;


import com.szzc.common.utils.lsk.bean.Account;

/**
 * Created by lx on 17-5-18.
 */
public class AccountResp extends BaseJson {

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
