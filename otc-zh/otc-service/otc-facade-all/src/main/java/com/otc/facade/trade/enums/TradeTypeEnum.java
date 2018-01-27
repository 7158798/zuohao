package com.otc.facade.trade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-5-10.
 */
public enum TradeTypeEnum {
    ALL(0,"全部"),
    BUY(1,"买"),
    SELL(2,"卖");

    private Integer code;

    private String desc;

    TradeTypeEnum(Integer code, String desc){
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


    private static Map<Integer, String> map = new HashMap<>();

    static {
        for (TradeTypeEnum tradeTypeEnum : TradeTypeEnum.values()) {
            map.put( tradeTypeEnum.code,tradeTypeEnum.desc);
        }
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

}
