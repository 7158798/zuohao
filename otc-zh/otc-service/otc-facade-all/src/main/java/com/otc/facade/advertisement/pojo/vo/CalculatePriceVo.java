package com.otc.facade.advertisement.pojo.vo;

import java.io.Serializable;

/**
 * Created by zygong on 17-5-11.
 */
public class CalculatePriceVo implements Serializable {
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

    private Long advertisementId;

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

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }
}
