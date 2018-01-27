package com.jucaifu.common.enums;

/**
 * EnumExceptionLevel
 *
 * @author scofieldcai
 *         <p>
 *         Created by scofieldcai-dev on 15/9/9.
 */
public enum EnumExceptionLevel implements IEnum<Integer> {

    /**
     * The FAIL.
     */
    FAIL(0, "失败"),
    /**
     * The SUCCESS.
     */
    SUCCESS(1, "成功"),
    /**
     * The WARN.
     */
    WARN(2, "警告"),
    /**
     * The INFO.
     */
    INFO(3, "提示信息"),;

    /**
     * The Value.
     */
    private Integer value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum exception level.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumExceptionLevel(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
