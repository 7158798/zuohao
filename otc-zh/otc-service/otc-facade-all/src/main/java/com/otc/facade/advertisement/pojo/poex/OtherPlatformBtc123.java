package com.otc.facade.advertisement.pojo.poex;

/**
 * Created by zygong on 17-3-24.
 */
public class OtherPlatformBtc123 {
    private String des;
    private boolean isSuc;
    private EthCoins datas;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isSuc() {
        return isSuc;
    }

    public void setSuc(boolean suc) {
        isSuc = suc;
    }

    public EthCoins getDatas() {
        return datas;
    }

    public void setDatas(EthCoins datas) {
        this.datas = datas;
    }
}
