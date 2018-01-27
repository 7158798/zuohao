package com.base.facade.version.enums;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yong on 15-11-26.
 */
public enum VersionAppCodeType {

    JUCAIFU("jucaifu", "聚财富"),
    XINXIN("xinxin", "鑫信") ;

    private String appCode;
    private String appName;

    // 保存appCode和appName
    private static Map<String, String> map = new HashMap<String, String>();

    // 保存appCode和appName
    private static Map<String, String> codeAndNameMap = new LinkedHashMap<String, String>();

    static {
        for (VersionAppCodeType s : VersionAppCodeType.values()) {
            map.put(s.appCode, s.appName);
            codeAndNameMap.put(s.appName, s.appCode);
        }
    }

    private VersionAppCodeType(String appCode, String appName) {
        this.appCode = appCode;
        this.appName = appName;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        VersionAppCodeType.map = map;
    }

    public static Map<String, String> getCodeAndNameMap() {
        return codeAndNameMap;
    }

    public static void setCodeAndNameMap(Map<String, String> codeAndNameMap) {
        VersionAppCodeType.codeAndNameMap = codeAndNameMap;
    }

    /**
     * 通过代码取得名称
     *
     * @param appCode
     * @return
     */
    public static String getCodeByName(String appCode) {
        return map.get(appCode);
    }

    /**
     * 通过名称取得代码
     *
     * @param appName
     * @return
     */
    public static String getNameByCode(String appName) {
        return codeAndNameMap.get(appName);
    }
}
