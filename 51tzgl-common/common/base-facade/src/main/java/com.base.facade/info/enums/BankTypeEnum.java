package com.base.facade.info.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yong on 16-1-14.
 */
public enum BankTypeEnum {

    BJYH("1", "北京银行"),
    GFYH("2", "广发银行"),
    HXYH("3", "华夏银行"),
    JSYH("4", "江苏银行"),
    JTYH("5", "交通银行"),
    PAYH("6", "平安银行"),
    PFYH("7", "浦发银行"),
    SHYH("8", "上海银行"),
    XYYH("9", "兴业银行"),
    ZSYH("10", "招商银行"),
    ZGGSYH("11", "工商银行"),
    ZGGDYH("12", "光大银行"),
    ZGJSYH("13", "建设银行"),
    ZGMSYH("14", "民生银行"),
    ZGNYYH("15", "农业银行"),
    ZGYH("16", "中国银行"),
    ZGYZCXYH("17", "邮政储蓄银行"),
    ZXYH("18", "中信银行"),
    OTHER_BANK("0", "其他银行");

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

    BankTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, String> map = new HashMap<String,String>();

    static {
        for (BankTypeEnum bankTypeEnum : BankTypeEnum.values()) {
            map.put( bankTypeEnum.desc,bankTypeEnum.code);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        BankTypeEnum.map = map;
    }
}
