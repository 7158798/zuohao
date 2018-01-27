package com.base.wallet.utils.lsk.resp;


import com.base.wallet.utils.lsk.bean.Transaction;

/**
 * Created by lx on 17-5-16.
 */
public class TransactionResp extends BaseJson {

    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
