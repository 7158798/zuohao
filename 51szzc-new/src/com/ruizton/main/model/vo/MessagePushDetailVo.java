package com.ruizton.main.model.vo;

import java.util.List;

/**
 * Created by zygong on 17-4-18.
 */
public class MessagePushDetailVo {
    private List<FapppushVo> fapppushVoList;
    private boolean isSms;

    public List<FapppushVo> getFapppushVoList() {
        return fapppushVoList;
    }

    public void setFapppushVoList(List<FapppushVo> fapppushVoList) {
        this.fapppushVoList = fapppushVoList;
    }

    public boolean isSms() {
        return isSms;
    }

    public void setSms(boolean sms) {
        isSms = sms;
    }
}
