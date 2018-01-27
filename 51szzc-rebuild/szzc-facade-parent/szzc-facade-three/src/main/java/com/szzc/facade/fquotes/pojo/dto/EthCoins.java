package com.szzc.facade.fquotes.pojo.dto;

/**
 * Created by zygong on 17-3-24.
 */
public class EthCoins {
    private String cName;
    private int coinId;
    private String coinName;
    private String coinSign;
    private String date;
    private int exeByRate;
    private int isRecomm;
    private String marketValue;
    private int moneyType;
    private String name;
    private String symbol;
    private TickerBtc123 ticker;
    private int type;

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinSign() {
        return coinSign;
    }

    public void setCoinSign(String coinSign) {
        this.coinSign = coinSign;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getExeByRate() {
        return exeByRate;
    }

    public void setExeByRate(int exeByRate) {
        this.exeByRate = exeByRate;
    }

    public int getIsRecomm() {
        return isRecomm;
    }

    public void setIsRecomm(int isRecomm) {
        this.isRecomm = isRecomm;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TickerBtc123 getTicker() {
        return ticker;
    }

    public void setTicker(TickerBtc123 ticker) {
        this.ticker = ticker;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
