package com.otc.facade.trade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-5-10.
 */
public enum TradeCancelEnum {

    BUYER_TIMEOUT(0,"买家支付超时"),

    BUYER_CANCEL(1,"买家取消交易"),

    APPEAL_CANCEL(2,"申诉判定");



    private Integer code;

    private String desc;

    TradeCancelEnum(Integer code, String desc){
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
        for (TradeCancelEnum tradeCancelEnum : TradeCancelEnum.values()) {
            map.put( tradeCancelEnum.code,tradeCancelEnum.desc);
        }
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

}
