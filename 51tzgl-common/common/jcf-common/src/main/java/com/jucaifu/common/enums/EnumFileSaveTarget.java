package com.jucaifu.common.enums;

import java.util.Map;

import com.jucaifu.common.util.EnumHelper;

/**
 * EnumFileSaveTarget
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/02/19.
 */
public enum EnumFileSaveTarget implements IEnum<String> {

    /**
     * The FASTDFS.
     */
    FASTDFS("01", "FastDFS文件系统"),

    /**
     * The QINIU.
     */
    QINIU("02", "七牛CDN"),

    ;


    private String value;

    private String desc;

    EnumFileSaveTarget(String value, String desc) {
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

    private static Map<String, EnumFileSaveTarget> sMap;


    /**
     * Gets by code.
     *
     * @param code the code
     * @return the by code
     */
    public static EnumFileSaveTarget getByCode(String code) {
        if (sMap == null) {
            sMap = EnumHelper.buildIEnumMap(EnumFileSaveTarget.class);
        }
        EnumFileSaveTarget _enum = sMap.get(code);
        return _enum ;
    }

}
