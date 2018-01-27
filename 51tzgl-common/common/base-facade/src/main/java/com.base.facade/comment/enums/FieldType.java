package com.base.facade.comment.enums;

/**
 * Created by liuxun on 16-10-13.
 */
public enum FieldType {

    VIEW("view_num","查看数量"),
    COMMENT("comment_num","评论数量"),
    LAUD("laud_num","点赞数量"),
    COLLECT("collect_num","收藏数量"),
    SHARE("share_num","分享数量");

    private String code;

    private String typeName;

    FieldType(String code, String typeName) {
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
