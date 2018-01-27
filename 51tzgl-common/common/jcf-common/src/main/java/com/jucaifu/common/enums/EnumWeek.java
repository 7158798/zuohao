package com.jucaifu.common.enums;

/**
 * EnumWeek
 *
 * @author scofieldcai
 *         <p>
 *         Created by scofieldcai-dev on 15/8/29.
 */
public enum EnumWeek implements IEnum<Integer> {

    /**
     * The MONDAY.
     */
    MONDAY(1, "星期一"),
    /**
     * The TUESDAY.
     */
    TUESDAY(2, "星期二"),
    /**
     * The WEDNESDAY.
     */
    WEDNESDAY(3, "星期三"),
    /**
     * The THURSDAY.
     */
    THURSDAY(4, "星期四"),
    /**
     * The FRIDAY.
     */
    FRIDAY(5, "星期五"),
    /**
     * The SATURDAY.
     */
    SATURDAY(6, "星期六") {
        @Override
        public boolean isRest() {
            return true;
        }
    },
    /**
     * The SUNDAY.
     */
    SUNDAY(0, "星期日") {
        @Override
        public boolean isRest() {
            return true;
        }
    };


    /**
     * The Value.
     */
    private Integer value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum week.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumWeek(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * Is rest.是否为假期
     *
     * @return the boolean
     */
    public boolean isRest() {
        return false;
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
