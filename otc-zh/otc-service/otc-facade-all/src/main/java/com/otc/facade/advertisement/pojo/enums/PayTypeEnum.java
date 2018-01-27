package com.otc.facade.advertisement.pojo.enums;

/**
 * 付款方式
 * Created by zygong on 17-4-26.
 */
public enum PayTypeEnum {
    ALIPAY(1, "支付宝"),
    WEIXIN(2, "微信"),
    BANKCARD(3, "银行转账"),
    CHECK(4, "支票"),
    CASH(5, "现金");

    private int value;
    private String name;

    PayTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static  PayTypeEnum getByKey(int value){
        PayTypeEnum[] list =  PayTypeEnum.values();
        for( PayTypeEnum i : list){
            if(i.getValue() == value ){
                return i;
            }
        }
        return null;
    }

    public static  PayTypeEnum getByValue(String name){
        PayTypeEnum[] list =  PayTypeEnum.values();
        for( PayTypeEnum i : list){
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
