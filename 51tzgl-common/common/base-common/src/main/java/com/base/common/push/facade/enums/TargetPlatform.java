package com.base.common.push.facade.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 发送平台
 *
 * @author zhaiyz
 */
public enum TargetPlatform {

    /**
     * 全部
     */
    ALL(0, "全部"),

    /**
     * iOS
     */
    IOS(1, "iOS"),

    /**
     * Android
     */
    ANDROID(2, "Android"),

    WEB(3,"Web");

    private Integer type;

    private String name;

    private static final Map<Integer, String> TARGET_PLATFORM_MAP = new LinkedHashMap<>();

    static {
        for (TargetPlatform targetPlatform : TargetPlatform.values()) {
            TARGET_PLATFORM_MAP.put(targetPlatform.getType(), targetPlatform.getName());
        }
    }

    TargetPlatform(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 通过type取得name
     *
     * @param type 类型
     * @return 名称
     */
    public static String getNameByType(Integer type) {
        return TARGET_PLATFORM_MAP.get(type);
    }

    /**
     * 判断目标平台类型是否不合法
     *
     * @param type 目标平台类型
     * @return 不合法返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isInvalidTargetPlatform(Integer type) {
        return TARGET_PLATFORM_MAP.get(type) == null ? true : false;
    }

    /**
     * 判断目标平台是否为全部
     *
     * @param type 目标平台
     * @return 如果是全部返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isAllTargetPlatform(Integer type) {
        return TargetPlatform.ALL.getType().compareTo(type) == 0;
    }

    /**
     * 判断目标平台是否为iOS
     *
     * @param type 目标平台
     * @return 如果是iOS平台返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isIosTargetPlatform(Integer type) {
        return TargetPlatform.IOS.getType().compareTo(type) == 0;
    }

    /**
     * 判断目标平台是否为Android
     *
     * @param type 目标平台
     * @return 如果是Android平台返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isAndroidTargetPlatform(Integer type) {
        return TargetPlatform.ANDROID.getType().compareTo(type) == 0;
    }



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
