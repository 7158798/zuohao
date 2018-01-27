package com.facade.core.wallet.enums;

/**
 * Created by zygong on 17-7-21.
 */
public enum TradeTypeEnum {
    BUY("buy", 0),
    SELL("sell", 1),
    SELL_MARKET("sell_market", 1),
        BUY_MARKET("buy_market", 1);

    TradeTypeEnum(String name, int value) {
        this.value = value;
        this.name = name;
    }

    public static TradeTypeEnum getByName(String name){
        TradeTypeEnum[] list =  TradeTypeEnum.values();
        for( TradeTypeEnum i : list){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }

    public static  TradeTypeEnum getByValue(int value){
        TradeTypeEnum[] list =  TradeTypeEnum.values();
        for( TradeTypeEnum i : list){
            if(i.getValue() == value){
                return i;
            }
        }
        return null;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
