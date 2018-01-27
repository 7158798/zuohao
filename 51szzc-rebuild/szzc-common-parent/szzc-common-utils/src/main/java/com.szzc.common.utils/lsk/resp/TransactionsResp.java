package com.szzc.common.utils.lsk.resp;


import com.szzc.common.utils.lsk.bean.Transaction;

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
