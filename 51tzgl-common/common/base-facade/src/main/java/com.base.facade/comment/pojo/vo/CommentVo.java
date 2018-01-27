package com.base.facade.comment.pojo.vo;

import com.base.facade.comment.pojo.poex.CommentInfo;
import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.List;

/**
 * Created by liuxun on 16-10-12.
 */
public class CommentVo extends BasePageVo {

    private Long relationId;
    // 00 帖子，01攻略
    private String type;

    private Long relationUserId;

    private Boolean readFlag;
    // 评论内容
    private String comment;
    // 标题
    private String relationTitle;
    // 关联信息集合
    private List<CommentInfo> relationList;
    // 排序类型
    private String sortType;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelationUserId() {
        return relationUserId;
    }

    public void setRelationUserId(Long relationUserId) {
        this.relationUserId = relationUserId;
    }

    public Boolean getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Boolean readFlag) {
        this.readFlag = readFlag;
    }

    public List<CommentInfo> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<CommentInfo> relationList) {
        this.relationList = relationList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRelationTitle() {
        return relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
