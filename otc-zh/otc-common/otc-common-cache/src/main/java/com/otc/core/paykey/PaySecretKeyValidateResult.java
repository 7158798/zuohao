package com.otc.core.paykey;

import com.jucaifu.common.enums.IEnum;
import com.jucaifu.common.util.EnumHelper;

import java.util.Map;

/**
 * PaySecretKeyValidateResult
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/11.
 */
public enum PaySecretKeyValidateResult implements IEnum<Integer> {
    /**
     * 重复提交
     */
    RESUBMIT(1, "重复提交错误"),
    /**
     * 失效
     */
    INVALID(2, "支付密钥key验证失效"),

    /**
     * 成功
     */
    SUCCESS(3, "支付密钥key验证成功"),;

    private Integer value;
    private String desc;

    PaySecretKeyValidateResult(Integer value, String desc) {
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

    private static Map<Integer, PaySecretKeyValidateResult> sMap;

    public static PaySecretKeyValidateResult getByEnumCode(Integer statusCode) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(PaySecretKeyValidateResult.class);
        }
        return sMap.get(statusCode);
    }
}
