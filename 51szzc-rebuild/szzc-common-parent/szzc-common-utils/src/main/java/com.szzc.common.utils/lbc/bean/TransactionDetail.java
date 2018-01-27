package com.szzc.common.utils.lbc.bean;

import java.util.List;

/**
 * Created by lx on 17-6-22.
 */
public class TransactionDetail {

    private List<Inputs> inputs;
    private List<Outputs> outputs;
    private Long lockTime;
    private Long version;

    public List<Inputs> getInputs() {
        return inputs;
    }

    public void setInputs(List<Inputs> inputs) {
        this.inputs = inputs;
    }

    public List<Outputs> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Outputs> outputs) {
        this.outputs = outputs;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
