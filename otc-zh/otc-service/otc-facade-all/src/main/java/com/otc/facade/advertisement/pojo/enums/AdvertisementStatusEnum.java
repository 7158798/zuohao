package com.otc.facade.advertisement.pojo.enums;

/**
 * 广告状态
 * Created by zygong on 17-4-26.
 */
public enum AdvertisementStatusEnum {
    CLOSED(2, "已关闭"),
    PUBLISHED(1, "已发布");

    private int value;
    private String name;

    AdvertisementStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static  AdvertisementStatusEnum getByKey(int value){
        AdvertisementStatusEnum[] list =  AdvertisementStatusEnum.values();
        for( AdvertisementStatusEnum i : list){
            if(i.getValue() == value ){
                return i;
            }
        }
        return null;
    }

    public static  AdvertisementStatusEnum getByValue(String name){
        AdvertisementStatusEnum[] list =  AdvertisementStatusEnum.values();
        for( AdvertisementStatusEnum i : list){
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
