package com.base.wallet.utils.lsk.resp;

/**
 * Created by lx on 17-5-18.
 */
public class SendTransactionResp extends BaseJson {
    // 交易id
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
