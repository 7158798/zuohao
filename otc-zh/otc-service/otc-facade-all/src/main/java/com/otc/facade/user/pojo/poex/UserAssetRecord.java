package com.otc.facade.user.pojo.poex;

import java.math.BigDecimal;

/**
 * Created by fenggq on 17-5-3.
 */
public class UserAssetRecord {
    private Long coinId;

    private BigDecimal total;

    private BigDecimal frozen;

    private String coinName;

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
