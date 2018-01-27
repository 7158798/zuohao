package com.otc.facade.advertisement.pojo.enums;

/**
 * 1、固定价格   2、溢价
 * Created by zygong on 17-5-9.
 */
public enum PriceTypeEnum {

    GDJG(1, "固定价格"),
    YJ(2, "溢价");

    private int value;
    private String name;

    PriceTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static  PriceTypeEnum getByKey(int value){
        PriceTypeEnum[] list =  PriceTypeEnum.values();
        for( PriceTypeEnum i : list){
            if(i.getValue() == value ){
                return i;
            }
        }
        return null;
    }

    public static  PriceTypeEnum getByValue(String name){
        PriceTypeEnum[] list =  PriceTypeEnum.values();
        for( PriceTypeEnum i : list){
            if(i.getName() == name){
                return i;
            }
        }
        return null;
    }

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
