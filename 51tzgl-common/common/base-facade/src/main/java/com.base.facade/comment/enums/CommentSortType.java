package com.base.facade.comment.enums;

/**
 * Created by lx on 17-1-5.
 */
public enum CommentSortType {

    // 查询时不传入即可
    SORT_00("00","按评论日期升序,id升序(默认排序)"),
    SORT_01("01","按评论日期降序，id降序");

    private String code;

    private String desc;

    CommentSortType(String code, String desc) {
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
}
