package com.base.wallet.utils.eth.bean;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by lx on 17-4-18.
 */
public class Debug {

    private BigInteger gas;

    private String returnValue;

    private List<StructLogs> structLogs;

    public BigInteger getGas() {
        return gas;
    }

    public void setGas(BigInteger gas) {
        this.gas = gas;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public List<StructLogs> getStructLogs() {
        return structLogs;
    }

    public void setStructLogs(List<StructLogs> structLogs) {
        this.structLogs = structLogs;
    }
}
