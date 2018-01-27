package com.base.facade.info.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zh on 16-8-23.
 */
public enum InfoRateStatus {

    INITIAL_RELEASE("0", "初始"),
    READY_RELEASE("1", "待发布"),
    FINISH_RELEASE("2","已发布"),
    LOSE_VALID("3","已失效"),
    INSERT_FAILDED("4","失败");

    private String code;

    private String desc;

    private InfoRateStatus(String code, String desc) {
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


    public static String getDescByCode(String code){
        String result = "";
        for (InfoRateStatus value : InfoRateStatus.values()){
            if (StringUtils.equals(code, value.code)){
                result = value.desc;
            }
        }

        return result;
    }

}

