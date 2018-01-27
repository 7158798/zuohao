package com.szzc.common.utils.siac.resp;

/**
 * Created by a123 on 17-6-19.
 */
public class InputsBean {
    private String parentid;
    private String fundtype;
    private boolean walletaddress;
    private String relatedaddress;
    private String value;

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public boolean isWalletaddress() {
        return walletaddress;
    }

    public void setWalletaddress(boolean walletaddress) {
        this.walletaddress = walletaddress;
    }

    public String getRelatedaddress() {
        return relatedaddress;
    }

    public void setRelatedaddress(String relatedaddress) {
        this.relatedaddress = relatedaddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
