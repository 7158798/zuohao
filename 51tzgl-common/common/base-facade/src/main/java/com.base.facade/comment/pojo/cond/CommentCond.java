package com.base.facade.comment.pojo.cond;



import com.base.facade.comment.pojo.poex.CommentInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lx on 16-11-3.
 */
public class CommentCond implements Serializable {

    private Long relationId;
    // 00 帖子，01攻略
    private String type;

    private Long relationUserId;

    private Boolean readFlag;
    // 关联信息集合
    private List<CommentInfo> relationList;
    // 谁评论的
    private Long author;
    //
    private Date sysdate;
    // 评论内容
    private String comment;
    // 标题
    private String relationTitle;

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

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
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
}
