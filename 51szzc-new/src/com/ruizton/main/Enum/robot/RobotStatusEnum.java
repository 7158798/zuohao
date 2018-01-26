package com.ruizton.main.Enum.robot;

import org.apache.commons.lang.StringUtils;

/**
 * Created by lx on 17-3-20.
 */
public enum RobotStatusEnum {

    INIT("00","待处理"),
    CANCEL("01","已取消"),
    CARRY_OUT("02","已完成");

    RobotStatusEnum(String code,String desc){
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
        for(RobotStatusEnum seat : RobotStatusEnum.values()) {
            if(StringUtils.equals(seat.getCode(), code)) {
                result_name = seat.getDesc();
                break;
            }
        }
        return result_name;
    }
}
