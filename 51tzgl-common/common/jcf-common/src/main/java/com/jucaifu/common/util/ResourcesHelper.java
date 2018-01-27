package com.jucaifu.common.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import com.jucaifu.common.enums.EnumLanguage;

/**
 * ResourcesHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/8.
 */
public class ResourcesHelper {

    private ResourcesHelper() {
    }

    /**
     * Gets locale.
     *
     * @param enumLanguage the enum language
     * @return the locale
     */
    public static Locale getLocale(EnumLanguage enumLanguage) {
        Locale locale = new Locale(enumLanguage.getLanguage(), enumLanguage.getCountry());
        return locale;
    }

    /**
     * Gets properties.
     *
     * @param baseName the base name
     * @param key      the key
     * @return the properties
     */
    public static String getProperties(String baseName, String key) {

        Locale locale = getLocale(EnumLanguage.CN_ZH);
        return getProperties(locale, baseName, key);
    }

    /**
     * Gets properties.
     *
     * @param locale   the locale
     * @param baseName the base name
     * @param key      the key
     * @return the properties
     */
    public static String getProperties(Locale locale, String baseName, String key) {

        String retValue = null;

        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
            retValue = (String) resourceBundle.getObject(key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retValue;
    }


    /**
     * Gets key list.
     *
     * @param baseName the base name
     * @return the key list
     */
    public static List<String> getKeyList(String baseName) {

        Locale locale = getLocale(EnumLanguage.CN_ZH);

        return getKeyList(locale, baseName);
    }

    /**
     * Gets key list.
     *
     * @param locale   the locale
     * @param baseName the base name
     * @return the key list
     */
    public static List<String> getKeyList(Locale locale, String baseName) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);

        List<String> resultList = new ArrayList<String>();

        Set<String> keySet = resourceBundle.keySet();

        resultList.addAll(keySet.stream().collect(Collectors.toList()));

        return resultList;
    }


    /**
     * Gets value.
     *
     * @param fileName the file name
     * @param key      the key
     * @return the value
     */
    public static String getValue(String fileName, String key) {

        String value = getProperties(fileName, key);

        return value;
    }

    /**
     * Gets value.
     *
     * @param locale   the locale
     * @param fileName the file name
     * @param key      the key
     * @return the value
     */
    public static String getValue(Locale locale, String fileName, String key) {

        String value = getProperties(locale, fileName, key);

        return value;
    }


    /**
     * Gets value.
     *
     * @param locale    the locale
     * @param fileName  the file name
     * @param key       the key
     * @param arguments the arguments
     * @return the value
     */
    public static String getValue(Locale locale, String fileName, String key, Object... arguments) {

        String pattern = getValue(locale, fileName, key);

        String value = null;

        if (arguments != null) {
            value = MessageFormat.format(pattern, arguments);
        } else {
            value = pattern;
        }

        return value;
    }


    /**
     * Gets value.
     *
     * @param fileName  the file name
     * @param key       the key
     * @param arguments the arguments
     * @return the value
     */
    public static String getValue(String fileName, String key, Object... arguments) {

        String pattern = getValue(fileName, key);
        String value = null;

        if (pattern != null) {

            if (arguments != null) {
                value = MessageFormat.format(pattern, arguments);
            } else {
                value = pattern;
            }
        }

        return value;
    }

}
