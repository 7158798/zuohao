package com.szzc.facade.wallet.pojo.cond;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 17-5-25.
 */
public class VirtualCoinTypeCond implements Serializable {

    private Boolean isRecharge;
    private Integer type;
    private Integer noType;
    private List<String> shortNameList;

    public Boolean getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(Boolean isRecharge) {
        this.isRecharge = isRecharge;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNoType() {
        return noType;
    }

    public void setNoType(Integer noType) {
        this.noType = noType;
    }

    public List<String> getShortNameList() {
        return shortNameList;
    }

    public void setShortNameList(List<String> shortNameList) {
        this.shortNameList = shortNameList;
    }
}
