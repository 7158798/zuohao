package com.jucaifu.common.enums;

/**
 * EnumHttpMethod
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/21.
 */
public enum EnumHttpMethod implements IEnum<String> {

    /**
     * The GET.
     */
    GET("GET", "rest:查询数据"),
    /**
     * The POST.
     */
    POST("POST", "rest:添加数据"),
    /**
     * The PUT.
     */
    PUT("PUT", "rest:更新数据"),
    /**
     * The DELETE.
     */
    DELETE("DELETE", "rest:删除数据"),
    /**
     * The OPTIONS.
     */
    OPTIONS("OPTIONS", "rest:辅助");

    /**
     * The Value.
     */
    private String value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum http method.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumHttpMethod(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
