package com.otc.facade.virtual.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-18.
 */
public enum VirtualRecordInStatus {

    REVOKE("02","用户撤销"),
    WAIT("03","等待确认"),
    SUCCESS("04","充值成功"),
    //ONE_TIME_AUDIT("10","一级审核"),
    TWO_TIME_AUDIT("11","二级审核"),
    THREE_TIME_AUDIT("12","三级审核");


    private String code;
    private String desc;

    public static Map<String,VirtualRecordInStatus> statusMap = new HashMap<>();

    static {
        for (VirtualRecordInStatus virtualRecordStatus : VirtualRecordInStatus.values()){
            statusMap.put(virtualRecordStatus.getCode(),virtualRecordStatus);
        }
    }

    VirtualRecordInStatus(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getDescByCode(String code){
        String desc = null;
        VirtualRecordInStatus status = statusMap.get(code);
        if (status != null){
            desc = status.desc;
        }
        return desc;
    }
}
