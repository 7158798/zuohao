package com.szzc.common.utils.siac.resp;

/**
 * Created by a123 on 17-6-19.
 */
public class OutputsBean {
    private String id;
    private String fundtype;
    private int maturityheight;
    private boolean walletaddress;
    private String relatedaddress;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public int getMaturityheight() {
        return maturityheight;
    }

    public void setMaturityheight(int maturityheight) {
        this.maturityheight = maturityheight;
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
