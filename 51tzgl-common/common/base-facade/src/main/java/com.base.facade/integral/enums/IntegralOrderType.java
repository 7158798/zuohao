package com.base.facade.integral.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zh on 16-11-9.
 */
public enum  IntegralOrderType {
    ALIPAY("alipay", "提现"),

    QB("qb", "Q币"),

    COUPON("coupon", "优惠券"),

    OBJECT("object", "实物"),

    PHONEBILL("phonebill", "话费"),

    PHONEFLOW("phoneflow", "流量"),

    VIRTUAL("virtual", "虚拟商品"),

    TURNTABLE("turntable", "大转盘"),

    SINGLELOTTERY("singleLottery", "单品抽奖"),

    HDTOOLLOTTERY("hdtoolLottery", "活动抽奖"),

    MANUALLOTTERY("manualLottery", "手动开奖"),

    GAMELOTTERY("gameLottery", "游戏");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    IntegralOrderType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (IntegralOrderType integralOrderType : IntegralOrderType.values()) {
            map.put( integralOrderType.code,integralOrderType.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        IntegralOrderType.map = map;
    }


    public static String getDescByCode(String code) {
        return map.get(code);
    }
}
