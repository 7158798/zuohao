package com.otc.facade.sys.enums;

/**
 * Created by lx on 17-5-1.
 */
public enum ResourceType {

    MENU("00","菜单");

    private String code;
    private String desc;

    ResourceType(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

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

}
