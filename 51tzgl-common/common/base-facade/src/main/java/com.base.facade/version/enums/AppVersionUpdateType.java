package com.base.facade.version.enums;

import java.util.Map;

import com.jucaifu.common.enums.IEnum;
import com.jucaifu.common.util.EnumHelper;

/**
 * Created by yong on 15-12-5.
 */
public enum AppVersionUpdateType implements IEnum<String> {

    /**
     * The NOTHING.
     */
    NOTHING("0", "没有跟新"),
    /**
     * The EXISTS_UPDATE.
     */
    EXISTS_UPDATE("1", "非强制跟新"),
    /**
     * The EXISTS_CAST_UPDATE.
     */
    EXISTS_CAST_UPDATE("2", "强制跟新");

    /**
     * The Value.
     */
    private String value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * @param value the value
     * @param desc  the desc
     */
    AppVersionUpdateType(String value, String desc) {
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

    private static Map<String, AppVersionUpdateType> sMap;


    public static AppVersionUpdateType getByCode(String code) {

        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(AppVersionUpdateType.class);
        }

        AppVersionUpdateType appOrderStatus = null;
        if (code != null) {
            appOrderStatus = sMap.get(code);
        }

        return appOrderStatus;

    }
}
