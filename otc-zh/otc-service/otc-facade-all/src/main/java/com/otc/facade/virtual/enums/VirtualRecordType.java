package com.otc.facade.virtual.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-18.
 */
public enum  VirtualRecordType {

    RECHARGE("00","充值"),
    WITHDRAW("01","提现");

    private String code;

    private String desc;

    VirtualRecordType(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Map<String,VirtualRecordType> typeMap = new HashMap<>();

    static {
        for (VirtualRecordType virtualRecordType : VirtualRecordType.values()) {
            typeMap.put(virtualRecordType.getCode(), virtualRecordType);
        }
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

    public static VirtualRecordType getTypeByCode(String code){
        return typeMap.get(code);
    }
}
