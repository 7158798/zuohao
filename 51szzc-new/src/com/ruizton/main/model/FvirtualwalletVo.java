package com.ruizton.main.model;

import java.math.BigDecimal;

/**
 * 用户虚拟币盈亏信息
 * Created by luwei on 17-3-6.
 */
public class FvirtualwalletVo {

    //虚拟币id
    private Integer fviFid;

    //虚拟币名称
    private String fviName;

    //总数量
    private BigDecimal ftotal;
    private String ftotalStr;

    //冻结数量
    private BigDecimal ffrozen;
    private String ffrozenStr;

    //可用数量
    private BigDecimal fable;
    private String fableStr;

    //最新市价
    private BigDecimal fprice;
    private String fpriceStr;

    //总市价
    private BigDecimal ftotalPrice;
    private String ftotalPriceStr;

    //成本价
    private BigDecimal fcostPrice;
    private String fcostPriceStr;

    //盈亏
    private BigDecimal fprofitLoss;
    private String fprofitLossStr;

    //盈亏比
    private BigDecimal fprofitLossRate;
    private String fprofitLossRateStr;

    private String logo;

    // 币种简写
    private String shortName;

    public Integer getFviFid() {
        return fviFid;
    }

    public void setFviFid(Integer fviFid) {
        this.fviFid = fviFid;
    }

    public String getFviName() {
        return fviName;
    }

    public void setFviName(String fviName) {
        this.fviName = fviName;
    }

    public BigDecimal getFtotal() {
        return ftotal;
    }

    public void setFtotal(BigDecimal ftotal) {
        this.ftotal = ftotal;
    }

    public String getFtotalStr() {
        return ftotalStr;
    }

    public void setFtotalStr(String ftotalStr) {
        this.ftotalStr = ftotalStr;
    }

    public BigDecimal getFfrozen() {
        return ffrozen;
    }

    public void setFfrozen(BigDecimal ffrozen) {
        this.ffrozen = ffrozen;
    }

    public String getFfrozenStr() {
        return ffrozenStr;
    }

    public void setFfrozenStr(String ffrozenStr) {
        this.ffrozenStr = ffrozenStr;
    }

    public BigDecimal getFable() {
        return fable;
    }

    public void setFable(BigDecimal fable) {
        this.fable = fable;
    }

    public String getFableStr() {
        return fableStr;
    }

    public void setFableStr(String fableStr) {
        this.fableStr = fableStr;
    }

    public BigDecimal getFprice() {
        return fprice;
    }

    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    public String getFpriceStr() {
        return fpriceStr;
    }

    public void setFpriceStr(String fpriceStr) {
        this.fpriceStr = fpriceStr;
    }

    public BigDecimal getFtotalPrice() {
        return ftotalPrice;
    }

    public void setFtotalPrice(BigDecimal ftotalPrice) {
        this.ftotalPrice = ftotalPrice;
    }

    public String getFtotalPriceStr() {
        return ftotalPriceStr;
    }

    public void setFtotalPriceStr(String ftotalPriceStr) {
        this.ftotalPriceStr = ftotalPriceStr;
    }

    public BigDecimal getFcostPrice() {
        return fcostPrice;
    }

    public void setFcostPrice(BigDecimal fcostPrice) {
        this.fcostPrice = fcostPrice;
    }

    public String getFcostPriceStr() {
        return fcostPriceStr;
    }

    public void setFcostPriceStr(String fcostPriceStr) {
        this.fcostPriceStr = fcostPriceStr;
    }

    public BigDecimal getFprofitLoss() {
        return fprofitLoss;
    }

    public void setFprofitLoss(BigDecimal fprofitLoss) {
        this.fprofitLoss = fprofitLoss;
    }

    public String getFprofitLossStr() {
        return fprofitLossStr;
    }

    public void setFprofitLossStr(String fprofitLossStr) {
        this.fprofitLossStr = fprofitLossStr;
    }

    public BigDecimal getFprofitLossRate() {
        return fprofitLossRate;
    }

    public void setFprofitLossRate(BigDecimal fprofitLossRate) {
        this.fprofitLossRate = fprofitLossRate;
    }

    public String getFprofitLossRateStr() {
        return fprofitLossRateStr;
    }

    public void setFprofitLossRateStr(String fprofitLossRateStr) {
        this.fprofitLossRateStr = fprofitLossRateStr;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
