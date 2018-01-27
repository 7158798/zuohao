package com.szzc.common.utils.siac.resp;

import java.util.List;

/**
 * Created by fenggq on 17-6-18.
 */
public class Transaction {


    /**
     * transactionid : 3a19798fde612d2b54d00175c82ead4e650f09040b0c6e8216106ddf095de7a3
     * confirmationheight : 110320
     * confirmationtimestamp : 1497801913
     * inputs : [{"parentid":"48c7265a8a5f5818bbfd706ca1ffe3abedb6953a4ad1cf9b4849c0dc013911a0","fundtype":"siacoin input","walletaddress":false,"relatedaddress":"1b3db72b7c5ec7fdd2ff4762c10baed46467c5d321799c6622b001a3c9e1752ab0df82cc35fa","value":"20000000000000000000000000"}]
     * outputs : [{"id":"7a5fe4ca78764e239e0b7df943d9d92c6672e46de3a1682a6c5cfa57eb09fa31","fundtype":"siacoin output","maturityheight":110320,"walletaddress":true,"relatedaddress":"67e389119dba751634bd724358b7762e69726984a7821c01de462b4ea2477fa79936fcefc4fc","value":"10000000000000000000000000"},{"id":"0000000000000000000000000000000000000000000000000000000000000000","fundtype":"miner fee","maturityheight":0,"walletaddress":false,"relatedaddress":"000000000000000000000000000000000000000000000000000000000000000089eb0d6a8a69","value":"10000000000000000000000000"}]
     */

    private String transactionid;
    private int confirmationheight;
    private int confirmationtimestamp;
    private List<InputsBean> inputs;
    private List<OutputsBean> outputs;

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public int getConfirmationheight() {
        return confirmationheight;
    }

    public void setConfirmationheight(int confirmationheight) {
        this.confirmationheight = confirmationheight;
    }

    public int getConfirmationtimestamp() {
        return confirmationtimestamp;
    }

    public void setConfirmationtimestamp(int confirmationtimestamp) {
        this.confirmationtimestamp = confirmationtimestamp;
    }

    public List<InputsBean> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputsBean> inputs) {
        this.inputs = inputs;
    }

    public List<OutputsBean> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<OutputsBean> outputs) {
        this.outputs = outputs;
    }


}
