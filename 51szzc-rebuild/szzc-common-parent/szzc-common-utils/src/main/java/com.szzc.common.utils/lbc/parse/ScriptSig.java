package com.szzc.common.utils.lbc.parse;

/**
 * Created by lx on 17-6-20.
 */
public class ScriptSig {

    /**
     * asm : 3045022100a78c20c9c3252166484e4941f14b749b3687ce40ecd0b7b47329b848ca9c13b502207a048701a7fab696e6dba491627b4440a495ca053a66db870c581c30e3e91f03[ALL] 026c2664079e72f765d4ae97be9c634b618828b8d8f0a85231eca67a9de6ea74e5
     * hex : 483045022100a78c20c9c3252166484e4941f14b749b3687ce40ecd0b7b47329b848ca9c13b502207a048701a7fab696e6dba491627b4440a495ca053a66db870c581c30e3e91f030121026c2664079e72f765d4ae97be9c634b618828b8d8f0a85231eca67a9de6ea74e5
     */

    private String asm;
    private String hex;

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
}
