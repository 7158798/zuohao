package com.jucaifu.common.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jucaifu.common.util.EnumHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * EnumDeviceType
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public enum EnumDeviceType implements IEnum<String> {

    /**
     * The ANDORID_MOBILD.
     */
    ANDORID_MOBILD("1", "android"),
    /**
     * The IOS_MOBILE.
     */
    IOS_MOBILE("2", "iOS"),
    /**
     * The ANDROID_PAD.
     */
    ANDROID_PAD("3", "androidPad"),
    /**
     * The IPAD.
     */
    IPAD("4", "ipad"),

    /**
     * WEB
     */
    WEB("5", "web");

    /**
     * The Value.
     */
    private String value;
    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Enum http method.
     *
     * @param value the value
     * @param desc  the desc
     */
    EnumDeviceType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static List<String> getDeviceTypeForApp() {
        List<String> list = new ArrayList<>();
        for (EnumDeviceType enumDeviceType : EnumDeviceType.values()) {
            if (!StringUtils.equals(enumDeviceType.getValue(), EnumDeviceType.WEB.getValue())) {
                list.add(enumDeviceType.getValue());
            }
        }
        return list;
    }

    public static List<String> getDeviceTypeForWeb() {
        List<String> list = new ArrayList<>();
        list.add(EnumDeviceType.WEB.getValue());
        return list;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    private static Map<String, EnumDeviceType> sMap;

    /**
     * Gets by code.
     *
     * @param code the code
     * @return the by code
     */
    public static String getByCode(String code) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(EnumDeviceType.class);
        }
        EnumDeviceType _enum = sMap.get(code);
        if (_enum != null) {
            return _enum.getDesc();
        } else {
            return null;
        }
    }

}
