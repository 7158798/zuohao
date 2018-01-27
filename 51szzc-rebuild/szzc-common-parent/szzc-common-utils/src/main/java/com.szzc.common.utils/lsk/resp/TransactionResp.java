package com.szzc.common.utils.lsk.resp;


import com.szzc.common.utils.lsk.bean.Transaction;

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
