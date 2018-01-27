package com.otc.api.console.ctrl.advertisement.request;

import com.otc.common.api.packet.web.request.WebApiBaseReq;

/**
 * 价格公式保存
 * Created by zygong on 17-4-28.
 */
public class PriceFormulaSaveReq extends WebApiBaseReq {
    /**
     * 币种，对应表字段为：t_price_formula.coin_id
     */
    private Long coinId;

    /**
     * 价格公式，对应表字段为：t_price_formula.price_formula
     */
    private String priceFormula;

    /**
     * 平台名称
     */
    private String platformName;

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
        this.priceFormula = priceFormula;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}
