package com.ruizton.main.Enum.batch;

import org.apache.commons.lang.StringUtils;

/**
 * Created by lx on 17-3-19.
 */
public enum BatchOrderStatusEnum {

    SUCCESS("00","已完成"),
    CANCEL("01","已取消");

    BatchOrderStatusEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public static String getDescByCode(String code) {
        String result_name = "";
        for(BatchOrderStatusEnum seat : BatchOrderStatusEnum.values()) {
            if(StringUtils.equals(seat.getCode(),code)) {
                result_name = seat.getDesc();
                break;
            }
        }
        return result_name;
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
