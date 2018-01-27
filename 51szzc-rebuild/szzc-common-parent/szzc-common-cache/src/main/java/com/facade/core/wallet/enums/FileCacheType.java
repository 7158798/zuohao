package com.facade.core.wallet.enums;

import com.jucaifu.common.enums.IEnum;
import com.jucaifu.common.util.EnumHelper;

import java.util.Map;

/**
 * EnumFileCache
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/17.
 */
public enum FileCacheType implements IEnum<String> {

    /**
     * 产品合同(产品id)
     */
    PRODUCT("1", "产品合同", "jcf_product_%s"),

    /**
     * 订单合同(订单id)
     */
    ORDER("2", "订单合同", "jcf_order_%s"),

    /**
     * 金融机构文件(文件id)
     */
    FINANCE_ORGANIZATION("3", "金融机构文件", "jcf_finance_organization_%s"),

    /**
     * 发行机构文件(文件id)
     */
    PUBLISH_ORGANIZATION("4", "发行机构文件", "jcf_publish_organization_%s"),;


    private String value;
    private String desc;
    private String keyFormat;


    FileCacheType(String value, String desc, String keyFormat) {
        this.value = value;
        this.desc = desc;
        this.keyFormat = keyFormat;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getKeyFormat() {
        return keyFormat;
    }

    private static Map<String, FileCacheType> sMap;

    /**
     * Gets by code.
     *
     * @param code the code
     * @return the by code
     */
    public static FileCacheType getByCode(String code) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(FileCacheType.class);
        }
        if (code != null) {
            return sMap.get(code);
        } else {
            return null;
        }
    }
}
