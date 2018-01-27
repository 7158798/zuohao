package com.base.facade.comment.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxun on 16-10-19.
 */
public enum ModuleType {

    POSTS("00","帖子"),
    RAIDERS("01","攻略"),
    COMMENT("02","评论"),
    NEWS("03","新闻"),
    COURSE("04", "课程");

    private String code;

    private String typeName;

    ModuleType(String code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    public static Map<String,ModuleType> periodMap;
    static {
        periodMap = new HashMap<>();
        for (ModuleType period : ModuleType.values()){
            periodMap.put(period.getCode(),period);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static String getTypeNameByCode(String code){
        String name = "";
        ModuleType type = periodMap.get(code);
        if (type != null){
            name = type.getTypeName();
        }
        return name;
    }
}
