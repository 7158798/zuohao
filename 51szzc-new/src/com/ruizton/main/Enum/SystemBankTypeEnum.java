package com.ruizton.main.Enum;

/**
 * Created by luwei on 17-3-14.
 */
public enum SystemBankTypeEnum {

    BANK(0, "银行卡"),
    ALIPAY(1, "支付宝");

    private int code;

    private String name;


    SystemBankTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
