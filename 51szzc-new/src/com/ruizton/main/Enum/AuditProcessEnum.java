package com.ruizton.main.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a123 on 17-3-14.
 */
public enum AuditProcessEnum {
    VIRTUAL_IN(1,"虚拟币充值审核"),
    VIRTUAL_OUT(2, "虚拟币提币审核"),
    CAPITAL_IN(3, "人民币充值审核"),
    CAPITAL_OUT(4, "人民币提现审核"),
    GOUP(5, "升级认证审核");


    private int code;
    private String desc;


    AuditProcessEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, String> map = new HashMap<>();

    static {
        for (AuditProcessEnum auditProcessEnum : AuditProcessEnum.values()) {
            map.put(auditProcessEnum.code, auditProcessEnum.desc);
        }
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, String> map) {
        AuditProcessEnum.map = map;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
