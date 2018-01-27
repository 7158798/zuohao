package com.jucaifu.common.enums;

/**
 * EnumSort
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public enum EnumSort implements IEnum<String> {

    /**
     * The ASC.
     */
    ASC("asc", "正序"),
    /**
     * The DESC.
     */
    DESC("desc", "逆序"),;

    /**
     * The Value.
     */
    private String value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum sort.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumSort(String value, String desc) {
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
