package com.otc.facade.advertisement.pojo.enums;

/**
 * Created by zygong on 17-5-15.
 */
public enum AdvertisementTypeEnum {
    BUY(1, "在线购买"),
    SELL(2, "在线出售");

    private int value;
    private String name;

    AdvertisementTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static  AdvertisementTypeEnum getByKey(int value){
        AdvertisementTypeEnum[] list =  AdvertisementTypeEnum.values();
        for( AdvertisementTypeEnum i : list){
            if(i.getValue() == value ){
                return i;
            }
        }
        return null;
    }

    public static  AdvertisementTypeEnum getByValue(String name){
        AdvertisementTypeEnum[] list =  AdvertisementTypeEnum.values();
        for( AdvertisementTypeEnum i : list){
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
