package com.jucaifu.common.enums;

/**
 * The enum Enum month.
 *
 * @author scofieldcai
 *         <p>
 *         Created by scofieldcai-dev on 15/8/31.
 */
public enum EnumMonth implements IEnum<Integer> {

    /**
     * The JANUARY.
     */
    JANUARY(1, "一月"),
    /**
     * The FEBRUARY.
     */
    FEBRUARY(2, "二月"),
    /**
     * The MARCH.
     */
    MARCH(3, "三月"),
    /**
     * The APRIL.
     */
    APRIL(4, "四月"),
    /**
     * The MAY.
     */
    MAY(5, "五月"),
    /**
     * The JUNE.
     */
    JUNE(6, "六月"),
    /**
     * The JULY.
     */
    JULY(7, "七月"),
    /**
     * The AUGUST.
     */
    AUGUST(8, "八月"),
    /**
     * The SEPTEMBER.
     */
    SEPTEMBER(9, "九月"),
    /**
     * The OCTOBER.
     */
    OCTOBER(10, "十月"),
    /**
     * The NOVEMBER.
     */
    NOVEMBER(11, "十一月"),
    /**
     * The DECEMBER.
     */
    DECEMBER(12, "十二月"),;

    /**
     * The Value.
     */
    private Integer value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum month.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumMonth(Integer value, String desc) {
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
