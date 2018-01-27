package com.szzc.common.utils.siac.resp;

import java.util.List;

/**
 * Created by a123 on 17-6-19.
 */
public class Transactions {
    private List<Transaction> confirmedtransactions;

    private List<Transaction> unconfirmedtransactions;

    public List<Transaction> getConfirmedtransactions() {
        return confirmedtransactions;
    }

    public void setConfirmedtransactions(List<Transaction> confirmedtransactions) {
        this.confirmedtransactions = confirmedtransactions;
    }

    public List<Transaction> getUnconfirmedtransactions() {
        return unconfirmedtransactions;
    }

    public void setUnconfirmedtransactions(List<Transaction> unconfirmedtransactions) {
        this.unconfirmedtransactions = unconfirmedtransactions;
    }
}
