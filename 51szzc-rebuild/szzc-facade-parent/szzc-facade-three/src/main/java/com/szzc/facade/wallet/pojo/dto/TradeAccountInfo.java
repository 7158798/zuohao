package com.szzc.facade.wallet.pojo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zygong on 17-7-19.
 */
public class TradeAccountInfo implements Serializable {
    /**
     * 币种名称
     */
    private String cnName;
    /**
     * 币种简称
     */
    private String shortName;
    /**
     * 总额
     */
    private String total;
    /**
     * 冻结
     */
    private String frozen;
    /**
     * 是否虚拟币
     */
    private boolean isCoin;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public boolean isCoin() {
        return isCoin;
    }

    public void setCoin(boolean coin) {
        isCoin = coin;
    }
}
