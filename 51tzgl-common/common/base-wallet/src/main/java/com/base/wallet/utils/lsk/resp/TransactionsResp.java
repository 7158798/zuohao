package com.base.wallet.utils.lsk.resp;


import com.base.wallet.utils.lsk.bean.Transaction;

import java.util.List;

/**
 * Created by lx on 17-5-16.
 */
public class TransactionsResp extends BaseJson {

    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
