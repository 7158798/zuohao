package com.otc.facade.trade.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-5-10.
 */
public enum TradeOperationEnum {

    USER_OPERATION(1,"用户操作"),

    ADMIN_OPERATION(2,"管理员处理"),

    SYSTEM_OPERATION(3,"系统处理");



    private Integer code;

    private String desc;

    TradeOperationEnum(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
