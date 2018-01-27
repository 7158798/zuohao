package com.szzc.common.utils.lbc.parse;

/**
 * Created by lx on 17-6-20.
 */
public class Vin {

    /**
     * sequence : 4294967295
     * txid : 7d285ba7f03a4f78c67c0df85cc719f7fd30688bf8018e5ddc73419154edad01
     * vout : 1
     */

    private long sequence;
    private String txid;
    private int vout;
    private ScriptSig scriptSig;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getVout() {
        return vout;
    }

    public void setVout(int vout) {
        this.vout = vout;
    }

    public ScriptSig getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(ScriptSig scriptSig) {
        this.scriptSig = scriptSig;
    }
}
