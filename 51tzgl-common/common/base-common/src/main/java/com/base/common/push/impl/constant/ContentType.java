package com.base.common.push.impl.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 消息主体类型
 *
 * @author zhijun
 * @author zhaiyz
 */
public enum ContentType {

    /**
     * 产品
     */
    PRODUCT(0, "产品"),

    /**
     * 文本
     */
    TEXT(1, "文本"),

    /**
     * 链接
     */
    LINK(2, "链接"),

    RedPackt(3,"红包活动");

    private Integer type;

    private String name;

    private static final Map<Integer, String> CONTENT_TYPE_MAP = new LinkedHashMap<>();

    static {
        for (ContentType msgType : ContentType.values()) {
            CONTENT_TYPE_MAP.put(msgType.getType(), msgType.getName());
        }
    }

    ContentType(Integer type, String name) {
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
        return CONTENT_TYPE_MAP.get(type);
    }

    /**
     * 判断类型是否不合法
     *
     * @param type 消息类型
     * @return 如果不可用返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isInvalidContentType(Integer type) {
        return CONTENT_TYPE_MAP.get(type) == null ? true : false;
    }

    /**
     * 判断内容类型是否为产品
     *
     * @param type 内容类型
     * @return 如果内容类型是产品返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isProductContentType(Integer type) {
        return ContentType.PRODUCT.getType().compareTo(type) == 0;
    }

    public static boolean isRedPacket(Integer type){
        return ContentType.RedPackt.getType().compareTo(type) == 0;
    }

    /**
     * 判断内容类型是否为文本
     *
     * @param type 内容类型
     * @return 如果内容类型是文本返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isTextContextType(Integer type) {
        return ContentType.TEXT.getType().compareTo(type) == 0;
    }

    /**
     * 判断内容类型是否为链接
     *
     * @param type 内容类型
     * @return 如果内容类型是链接返回<code>true</code>，否则返回<code>false</code>
     */
    public static boolean isLinkContextType(Integer type) {
        return ContentType.LINK.getType().compareTo(type) == 0;
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
