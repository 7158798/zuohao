package com.otc.facade.virtual.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-20.
 */
public enum VirtualCoinStatus {

    ENABLED("00","启用"),
    DISABLED("01","禁用");

    private String code;
    private String desc;

    VirtualCoinStatus(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Map<String,VirtualCoinStatus> statusMap = new HashMap<>();

    static {
        for (VirtualCoinStatus virtualCoinStatus : VirtualCoinStatus.values()){
            statusMap.put(virtualCoinStatus.getCode(),virtualCoinStatus);
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


    public static String getDescByCode(String code){
        String desc = null;
        VirtualCoinStatus status = statusMap.get(code);
        if (status != null)
            desc = status.getDesc();
        return desc;
    }


}
