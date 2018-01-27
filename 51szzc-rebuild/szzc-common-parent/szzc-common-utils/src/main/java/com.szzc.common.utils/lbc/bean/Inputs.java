package com.szzc.common.utils.lbc.bean;

import java.util.List;

/**
 * Created by lx on 17-6-22.
 */
public class Inputs {

    /**
     * address : bEEvcDjx7CnwjJwrPieUrKiS6mcuXdGLmE
     * is_coinbase : false
     * num_sig : 1
     * prevout_hash : 7d285ba7f03a4f78c67c0df85cc719f7fd30688bf8018e5ddc73419154edad01
     * prevout_n : 1
     * pubkeys : ["026c2664079e72f765d4ae97be9c634b618828b8d8f0a85231eca67a9de6ea74e5"]
     * scriptSig : 483045022100a78c20c9c3252166484e4941f14b749b3687ce40ecd0b7b47329b848ca9c13b502207a048701a7fab696e6dba491627b4440a495ca053a66db870c581c30e3e91f030121026c2664079e72f765d4ae97be9c634b618828b8d8f0a85231eca67a9de6ea74e5
     * sequence : 4294967295
     * signatures : ["3045022100a78c20c9c3252166484e4941f14b749b3687ce40ecd0b7b47329b848ca9c13b502207a048701a7fab696e6dba491627b4440a495ca053a66db870c581c30e3e91f03"]
     * x_pubkeys : ["026c2664079e72f765d4ae97be9c634b618828b8d8f0a85231eca67a9de6ea74e5"]
     */

    private String address;
    private Boolean is_coinbase;
    private Integer num_sig;
    private String prevout_hash;
    private Integer prevout_n;
    private String scriptSig;
    private Long sequence;
    private List<String> pubkeys;
    private List<String> signatures;
    private List<String> x_pubkeys;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIs_coinbase() {
        return is_coinbase;
    }

    public void setIs_coinbase(boolean is_coinbase) {
        this.is_coinbase = is_coinbase;
    }

    public Integer getNum_sig() {
        return num_sig;
    }

    public void setNum_sig(Integer num_sig) {
        this.num_sig = num_sig;
    }

    public String getPrevout_hash() {
        return prevout_hash;
    }

    public void setPrevout_hash(String prevout_hash) {
        this.prevout_hash = prevout_hash;
    }

    public Integer getPrevout_n() {
        return prevout_n;
    }

    public void setPrevout_n(Integer prevout_n) {
        this.prevout_n = prevout_n;
    }

    public String getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(String scriptSig) {
        this.scriptSig = scriptSig;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public List<String> getPubkeys() {
        return pubkeys;
    }

    public void setPubkeys(List<String> pubkeys) {
        this.pubkeys = pubkeys;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public List<String> getX_pubkeys() {
        return x_pubkeys;
    }

    public void setX_pubkeys(List<String> x_pubkeys) {
        this.x_pubkeys = x_pubkeys;
    }
}
