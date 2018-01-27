package com.base.facade.info.enums;

/**
 * Created by zh on 16-8-23.
 */
public enum InfoRateGainWay  {

    IINSERT_BY_FILE("0", "文件导入"),

    INSERT_BY_PAGE("1", "页面新增"),

    INSERT_BY_CRAWLER("2","爬虫"),

    INSERT_BY_INTERFACE("3","接口");

    private String code;

    private String typeName;

    private InfoRateGainWay(String code, String typeName) {
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
