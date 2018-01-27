package com.jucaifu.common.util.mask;

import com.jucaifu.common.enums.IEnum;

/**
 * EnumMaskType
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/25.
 */
public enum EnumMaskType implements IEnum<Integer> {

    BANKCARD_NO(1, "隐藏银行卡号"),

    IDCARD_NO(2, "隐藏身份证号码"),

    PHONE_NO(3, "隐藏手机号码");

    private Integer value;

    private String desc;

    EnumMaskType(Integer value, String desc) {
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
