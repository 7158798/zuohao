package com.jucaifu.core.job;

import com.jucaifu.common.enums.IEnum;

/**
 * JobCycleType
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/29.
 */
public enum JobCycleType implements IEnum<Integer> {

    /**
     * The CUSTOM.
     */
    CUSTOM(0, "执行周期:自定义"),
    /**
     * The MINUTE.
     */
    MINUTE(1, "执行周期:分钟"),
    /**
     * The HOUR.
     */
    HOUR(2, "执行周期:小时"),
    /**
     * The DAY.
     */
    DAY(3, "执行周期:日"),
    /**
     * The WEEK.
     */
    WEEK(4, "执行周期:周"),
    /**
     * The MONTH.
     */
    MONTH(5, "执行周期:月"),
    /**
     * The YEAR.
     */
    YEAR(6, "执行周期:年"),;

    /**
     * The Code.
     */
    public Integer value;
    /**
     * The Desc.
     */
    public String desc;

    /**
     * Instantiates a new Enum sex.
     *
     * @param value the value
     * @param desc the desc
     */
    JobCycleType(Integer value, String desc) {
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
