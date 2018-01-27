package com.szzc.common.utils.lbc.parse;

import java.util.List;

/**
 * Created by lx on 17-6-20.
 */
public class Source {

    /**
     * size : 226
     * locktime : 0
     * claims : []
     * txid : 95001809afccede23ebc80df7f04d14fce96d4005e225471050201500ee2e2dd
     * block_height : 184494
     * type : transactions
     * version : 1
     */

    private int size;
    private int locktime;
    private String txid;
    private int block_height;
    private String type;
    private int version;
    private List<Vin> vin;
    private List<Vout> vout;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLocktime() {
        return locktime;
    }

    public void setLocktime(int locktime) {
        this.locktime = locktime;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getBlock_height() {
        return block_height;
    }

    public void setBlock_height(int block_height) {
        this.block_height = block_height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Vin> getVin() {
        return vin;
    }

    public void setVin(List<Vin> vin) {
        this.vin = vin;
    }

    public List<Vout> getVout() {
        return vout;
    }

    public void setVout(List<Vout> vout) {
        this.vout = vout;
    }
}
