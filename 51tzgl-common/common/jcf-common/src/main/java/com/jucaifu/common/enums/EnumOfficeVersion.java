package com.jucaifu.common.enums;

/**
 * EnumOfficeVersion
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/21.
 */
public enum EnumOfficeVersion implements IEnum<Integer> {

    OFFICE2003(1, "office2003"),
    OFFICE2007(2, "office2007"),;

    private Integer value;
    private String desc;

    EnumOfficeVersion(Integer value, String desc) {
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
