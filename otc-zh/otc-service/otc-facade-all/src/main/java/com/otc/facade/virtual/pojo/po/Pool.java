package com.otc.facade.virtual.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class Pool extends BasePo {

    /**
     * 对应表字段为：t_pool.coin_id
     */
    private Long coinId;

    /**
     * 对应表字段为：t_pool.address
     */
    private String address;

    /**
     * 对应表字段为：t_pool.status
     */
    private String status;

    /**
     * 对应表字段为：t_pool.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}