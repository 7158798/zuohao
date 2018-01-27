package com.otc.facade.virtual.enums;

/**
 * Created by lx on 17-4-21.
 */
public enum PoolStatus {

    USED("00","已使用"),
    UNUSED("01","未使用");

    private String code;

    private String desc;

    PoolStatus(String code,String desc){
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
