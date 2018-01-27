package com.otc.facade.user.enums;

import com.jucaifu.common.util.EnumHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-4-26.
 */
public enum UserStatusEnum {

    COMMON("01","正常"),

    FORBIDDEN("02","禁用"),

    FROZEN("03","冻结");

    private String code;

    private String desc;

    UserStatusEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
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


    public static String getNameByCode(String code) {
        return map.get(code);
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            map.put( userStatusEnum.code,userStatusEnum.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }
}
