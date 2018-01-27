package com.otc.facade.trade.enums;

/**
 * Created by fenggq on 17-5-10.
 */
public enum TradeSideEnum {

    BUY(1,"买家"),

    SELL(2,"卖家");



    private Integer code;

    private String desc;

    TradeSideEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
