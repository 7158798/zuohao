package com.otc.facade.base;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 后台统计
 * Created by zygong on 17-5-12.
 */
public class CountVo implements Serializable {
    private String countName;
    private BigDecimal countTotal;

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public BigDecimal getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(BigDecimal countTotal) {
        this.countTotal = countTotal;
    }
}
