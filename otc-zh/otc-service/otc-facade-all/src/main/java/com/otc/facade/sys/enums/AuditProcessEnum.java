package com.otc.facade.sys.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a123 on 17-3-14.
 */
public enum AuditProcessEnum {
    VIRTUAL_IN("00","虚拟币充值审核"),
    VIRTUAL_OUT("01", "虚拟币提币审核"),
    CAPITAL_IN("02", "人民币充值审核"),
    CAPITAL_OUT("03", "人民币提现审核"),
    GOUP("04", "升级认证审核"),
    TRADE_CONFRIM("05","申诉放币"),
    TRADE_CANCEL("06","申诉取消"),
    TRADE_CONFRIM_RUN("07","确认放币(进行中)"),
    ;


    private String code;
    private String desc;


    AuditProcessEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (AuditProcessEnum auditProcessEnum : AuditProcessEnum.values()) {
            map.put(auditProcessEnum.code, auditProcessEnum.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        AuditProcessEnum.map = map;
    }

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
}
