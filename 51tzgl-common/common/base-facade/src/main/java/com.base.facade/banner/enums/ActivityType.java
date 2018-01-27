/** 
 * Creation Date:Sep 7, 201510:06:27 AM 
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved. 
 */
package com.base.facade.banner.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * App首页导航类型
 * 
 * @author yangyy
 */
public enum ActivityType {

    PRODUCT("0", "产品"),

    LINK("1", "链接"),

    DUIBA("2","兑吧");

    private String code;

    private String typeName;

    private ActivityType(String code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    private static Map<String, String> map = new HashMap<String, String>();

    static {
        for (ActivityType appHomeBannerType : ActivityType.values()) {
            map.put(appHomeBannerType.getCode(), appHomeBannerType.getTypeName());
        }
    }

    public String getTypeNameByCode(String code) {
        return map.get(code);
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

}
