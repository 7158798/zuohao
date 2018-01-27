package com.szzc.facade.fentrustLog.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CoinShorNameAndFtrademappingId extends BasePo implements Serializable {
    /**
     * 简称，对应表字段为：fvirtualcointype.fShortName
     */
    private String shortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}