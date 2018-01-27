package com.otc.facade.advertisement.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class PriceFormula extends BasePo implements Serializable {

    /**
     * 币种，对应表字段为：t_price_formula.coin_id
     */
    private Long coinId;

    /**
     * 价格公式，对应表字段为：t_price_formula.price_formula
     */
    private String priceFormula;

    /**
     * 创建时间，对应表字段为：t_price_formula.createtime
     */
    private Date createtime;

    /**
     * 更新时间，对应表字段为：t_price_formula.updatetime
     */
    private Date updatetime;

    /**
     * 删除（0、未删除  1、已删除 ），对应表字段为：t_price_formula.is_delete
     */
    private Boolean isDelete;

    /**
     * 平台名称，对应表字段为：t_price_formula.platform_name
     */
    private String platformName;

    private String coinName;

    private static final long serialVersionUID = 1L;

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public String getPriceFormula() {
        return priceFormula;
    }

    public void setPriceFormula(String priceFormula) {
        this.priceFormula = priceFormula == null ? null : priceFormula.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}