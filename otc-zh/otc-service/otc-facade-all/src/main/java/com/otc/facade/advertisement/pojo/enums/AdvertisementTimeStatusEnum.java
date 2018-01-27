package com.otc.facade.advertisement.pojo.enums;

/**
 * Created by zygong on 17-5-24.
 */
public enum AdvertisementTimeStatusEnum {

    QBRQ(1, "全部日期 "),
    GZR(2, "仅工作日"),
    ZMJJR(3, "仅周末节假日");

    private int value;
    private String name;

    AdvertisementTimeStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static  AdvertisementTimeStatusEnum getByKey(int value){
        AdvertisementTimeStatusEnum[] list =  AdvertisementTimeStatusEnum.values();
        for( AdvertisementTimeStatusEnum i : list){
            if(i.getValue() == value ){
                return i;
            }
        }
        return null;
    }

    public static  AdvertisementTimeStatusEnum getByValue(String name){
        AdvertisementTimeStatusEnum[] list =  AdvertisementTimeStatusEnum.values();
        for( AdvertisementTimeStatusEnum i : list){
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
