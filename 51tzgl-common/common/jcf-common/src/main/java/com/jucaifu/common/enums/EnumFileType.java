package com.jucaifu.common.enums;

/**
 * Created by yong on 16-3-7.
 */
public enum EnumFileType {

    PDF("pdf", "PDF"),
    XLSX("xlsx", "Excel"),
    DOC("doc", "DOC"),
    TXT("txt", "TXT");

    private String type;

    private String desc;

    EnumFileType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
