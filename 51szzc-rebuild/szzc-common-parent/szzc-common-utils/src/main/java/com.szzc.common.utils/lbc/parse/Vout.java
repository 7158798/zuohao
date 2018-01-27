package com.szzc.common.utils.lbc.parse;

import java.math.BigDecimal;

/**
 * Created by lx on 17-6-20.
 */
public class Vout {

    /**
     * value : 14.99
     * n : 0
     */

    private BigDecimal value;
    private int n;
    private ScriptPubKey scriptPubKey;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public ScriptPubKey getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(ScriptPubKey scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }
}
