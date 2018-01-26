package com.ruizton.main.Enum;

/**
 * Created by lx on 17-2-20.
 */
public enum AccountTypeEnum {

    OK_COIN(1,"okCoin");

    private Integer code;

    private String desc;

    AccountTypeEnum(Integer code,String desc){
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
