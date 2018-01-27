package com.otc.facade.virtual.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirtualWallet extends BasePo {

    /**
     * 对应表字段为：t_virtual_wallet.coin_id
     */
    private Long coinId;

    /**
     * 对应表字段为：t_virtual_wallet.total
     */
    private BigDecimal total;

    /**
     * 对应表字段为：t_virtual_wallet.frozen
     */
    private BigDecimal frozen;

    /**
     * 对应表字段为：t_virtual_wallet.user_id
     */
    private Long userId;

    /**
     * 对应表字段为：t_virtual_wallet.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_virtual_wallet.modified_date
     */
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getFrozen() {
        return frozen;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}