package com.otc.facade.sys.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lx on 17-4-26.
 */
public enum EmployeeStatus {

    ENABLED("00","启用"),
    DISABLED("01","禁用");

    private String code;
    private String desc;

    EmployeeStatus(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public static Map<String,EmployeeStatus> statusMap = new HashMap<>();

    static {
        for (EmployeeStatus employeeStatus : EmployeeStatus.values()){
            statusMap.put(employeeStatus.getCode(),employeeStatus);
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
        EmployeeStatus status = statusMap.get(code);
        if (status != null)
            desc = status.getDesc();
        return desc;
    }
}
