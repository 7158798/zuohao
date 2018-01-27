package com.jucaifu.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jucaifu.common.enums.IEnum;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * EnumHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/31.
 */
public class EnumHelper {
    private EnumHelper() {
    }

    /**
     * Extract enum name list.
     *
     * @param clz the clz
     * @return the list
     */
    public static List<String> extractEnumNameList(Class<? extends Enum> clz) {

        ArrayList<String> enumNameList = null;

        if (clz.isEnum()) {
            enumNameList = new ArrayList<String>();

            Enum[] enums = clz.getEnumConstants();
            for (Enum en : enums) {
                enumNameList.add(en.name());
            }
        }

        return enumNameList;
    }


    /**
     * Build i enum map.
     *
     * @param <ValueType> the type parameter
     * @param <EnumType>  the type parameter
     * @param clz         the clz
     * @return the map
     */
    public static <ValueType, EnumType> Map<ValueType, EnumType> buildIEnumMap(Class<? extends Enum> clz) {

        HashMap<ValueType, EnumType> enumMap = null;

        if (clz.isEnum()) {
            enumMap = new HashMap<ValueType, EnumType>();
            Enum[] enums = clz.getEnumConstants();
            for (Enum en : enums) {
                if (en instanceof IEnum) {
                    ValueType value = (ValueType) ((IEnum) en).getValue();
                    enumMap.put(value, (EnumType) en);
                }
            }
        }
        return enumMap;
    }


    /**
     * Extract enum prop value list.
     *
     * @param <T>      the type parameter
     * @param clz      the clz
     * @param propName the prop name
     * @return the list
     */
    public static <T> List<T> extractEnumPropValueList(Class<? extends Enum> clz, String propName) {

        ArrayList<T> propValueList = null;

        if (clz.isEnum()) {
            propValueList = new ArrayList<T>();
            Enum[] enums = clz.getEnumConstants();

            T propVaule = null;
            for (Enum en : enums) {
                try {
                    propVaule = (T) PropertyUtils.getProperty(en, propName);
                    propValueList.add(propVaule);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return propValueList;
    }

}
