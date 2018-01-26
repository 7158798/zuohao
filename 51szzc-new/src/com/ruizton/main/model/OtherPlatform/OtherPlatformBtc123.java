package com.ruizton.main.model.OtherPlatform;

import java.util.List;

/**
 * Created by zygong on 17-3-24.
 */
public class OtherPlatformBtc123 {
    private String des;
    private boolean isSuc;
//    private List<DatasBtc123> datas;
    private Datas datas;

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

    public Datas getDatas() {
        return datas;
    }

    public void setDatas(Datas datas) {
        this.datas = datas;
    }
}
