package com.ruizton.main.Enum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zh on 16-10-20.
 */
public enum ModuleEnum {

    VIRTUAL_OPERATION(ModuleConstont.VIRTUAL_OPERATION, "虚拟货币管理"),
    CYN_OPERATION(ModuleConstont.CYN_OPERATION, "人民币操作管理"),
    USER_OPERATION(ModuleConstont.USER_OPERATION,"会员管理"),
    ARTICLE_OPEATION(ModuleConstont.ARTICLE_OPERATION,"资讯管理"),
    SYSTEM_OPERATION(ModuleConstont.SYSTEM_OPERATION,"系统管理");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ModuleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, String> map = new HashMap<String, String>();

    static {
        for (ModuleEnum moduleEnum : ModuleEnum.values()) {
            map.put( moduleEnum.code,moduleEnum.desc);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        ModuleEnum.map = map;
    }

    public static List<String> getAllDesc(){
        List<String> allDesc = new ArrayList<String>();
        for (ModuleEnum moduleEnum : ModuleEnum.values()) {
            allDesc.add(moduleEnum.desc);
        }
        return allDesc;
    }

}
