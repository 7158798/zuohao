package com.otc.facade.trade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-5-11.
 */
public enum TradeJudgeLevelEnum {

    GOOD(5,"好评"),

    COMMON(3,"中评"),

    BAD(1,"差评");

    private Integer code;

    private String desc;


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

    TradeJudgeLevelEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }


    private static Map<Integer, String> map = new HashMap<>();

    static {
        for (TradeJudgeLevelEnum levelEnum : TradeJudgeLevelEnum.values()) {
            map.put( levelEnum.code,levelEnum.desc);
        }
    }

    public static Map<Integer, String> getMap() {
        return map;
    }


    public static boolean isGood(Integer code){
        if(code == null){
            return false;
        }
        return code == GOOD.getCode();
    }

    public static boolean isBad(Integer code){
        if(code == null){
            return false;
        }
        return code == BAD.getCode();
    }

    public static boolean isCommon(Integer code){
        if(code == null){
            return false;
        }
        return code == COMMON.getCode();
    }
}
