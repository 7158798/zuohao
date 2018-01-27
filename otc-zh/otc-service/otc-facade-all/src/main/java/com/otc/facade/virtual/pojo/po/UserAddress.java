package com.otc.facade.virtual.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class UserAddress extends BasePo {

    /**
     * 对应表字段为：t_user_address.user_id
     */
    private Long userId;

    /**
     * 对应表字段为：t_user_address.coin_id
     */
    private Long coinId;

    /**
     * 对应表字段为：t_user_address.address
     */
    private String address;

    /**
     * 对应表字段为：t_user_address.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}