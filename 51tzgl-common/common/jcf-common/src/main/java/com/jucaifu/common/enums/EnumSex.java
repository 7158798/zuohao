package com.jucaifu.common.enums;

/**
 * EnumSex
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/29.
 */
public enum EnumSex implements IEnum<Integer> {

    /**
     * The MALE.
     */
    MALE(1, "男"),
    /**
     * The FEMALE.
     */
    FEMALE(2, "女"),;
    /**
     * The Code.
     */
    private Integer value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum sex.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumSex(Integer value, String desc) {
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
