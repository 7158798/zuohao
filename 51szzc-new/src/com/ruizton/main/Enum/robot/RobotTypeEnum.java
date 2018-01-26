package com.ruizton.main.Enum.robot;

import org.apache.commons.lang.StringUtils;

/**
 * Created by lx on 17-3-20.
 */
public enum RobotTypeEnum {

    BUY("00","买入"),
    SELL("01","卖出");

    RobotTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

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

    public static String getDescByCode(String code) {
        String result_name = "";
        for(RobotTypeEnum seat : RobotTypeEnum.values()) {
            if(StringUtils.equals(seat.getCode(), code)) {
                result_name = seat.getDesc();
                break;
            }
        }
        return result_name;
    }
}
