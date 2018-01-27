package com.otc.api.web.ctrl.advertisement.request;

/**
 * 计算公式
 * Created by zygong on 17-4-27.
 */
public class CalculationFormulaReq{
    /**
     * 单价
     */
    private String price;
    /**
     * 币种id
     */
    private Long symbol;

    /**
     * 溢价率
     */
    private String premiumRate;

    /**
     * 平台名称
     */
    private String platform;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getSymbol() {
        return symbol;
    }

    public void setSymbol(Long symbol) {
        this.symbol = symbol;
    }

    public String getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(String premiumRate) {
        this.premiumRate = premiumRate;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
