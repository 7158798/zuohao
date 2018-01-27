package com.base.facade.version.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyy on 15-12-2.
 */
public enum PositionType {

    DISCOVERY(1, "发现"),

    MANAGE_MONEY(2,"理财"),

    EXP_MONEY(3, "体验金"),

    INVATE_FRIENDLY(4, "邀请好友"),

    APP_HOME(5, "精选推荐"),

    SHARE(6, "分享");

    private int code;

    private String name;

    static Map<Integer, String> map = new HashMap<Integer, String>();

    static {
        for (PositionType positionType : PositionType.values()) {
            map.put(positionType.getCode(), positionType.getName());
        }
    }

    PositionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameBycode(int code) {
        return map.get(code);
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
