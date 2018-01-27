package com.base.wallet.utils.eth.bean;

import java.util.List;

/**
 * Created by lx on 17-4-18.
 */
public class StructLogs {

    private Integer pc;

    private String op;
    private String gas;

    private String gasCost;

    private String depth;

    private String error;
    // 合约地址的数据
    private List<String> stack;
    private List<String> memory;
    private List<String> storage;

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasCost() {
        return gasCost;
    }

    public void setGasCost(String gasCost) {
        this.gasCost = gasCost;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public List<String> getMemory() {
        return memory;
    }

    public void setMemory(List<String> memory) {
        this.memory = memory;
    }

    public List<String> getStorage() {
        return storage;
    }

    public void setStorage(List<String> storage) {
        this.storage = storage;
    }
}
