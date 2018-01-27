package com.base.facade.info.enums;

/**
 * Created by zh on 16-8-22.
 */
public enum  InfoRateType {

    DEPOSIT_RATE("0", "存款利率"),

    LOAN_RATE("1", "贷款利率");

    private String code;

    private String typeName;

    private InfoRateType(String code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
