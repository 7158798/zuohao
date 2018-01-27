package com.otc.facade.virtual.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Withdrawfees extends BasePo implements Serializable {

    /**
     * 手续费，对应表字段为：t_withdrawfees.fee
     */
    private BigDecimal fee;

    /**
     * 用户等级，对应表字段为：t_withdrawfees.level
     */
    private Integer level;

    /**
     * 对应表字段为：t_withdrawfees.vid
     */
    private Long vid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }
}