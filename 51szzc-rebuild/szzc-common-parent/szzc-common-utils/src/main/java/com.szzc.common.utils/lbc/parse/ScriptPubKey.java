package com.szzc.common.utils.lbc.parse;

import java.util.List;

/**
 * Created by lx on 17-6-20.
 */
public class ScriptPubKey {


    /**
     * addresses : ["bRtKUbN3j9XPhJNEHzwN797hx3ardrr7vF"]
     * asm : OP_DUP OP_HASH160 904fb38431c189ca2a8a075451b55b7ba5273811 OP_EQUALVERIFY OP_CHECKSIG
     * hex : 76a914904fb38431c189ca2a8a075451b55b7ba527381188ac
     * type : pubkeyhash
     * reqSigs : 1
     */

    private String asm;
    private String hex;
    private String type;
    private int reqSigs;
    private List<String> addresses;

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReqSigs() {
        return reqSigs;
    }

    public void setReqSigs(int reqSigs) {
        this.reqSigs = reqSigs;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
}
