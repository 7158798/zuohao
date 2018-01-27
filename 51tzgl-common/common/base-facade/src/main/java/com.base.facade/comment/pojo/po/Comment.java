package com.base.facade.comment.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class Comment extends BasePo {

    /**
     * 对应表字段为：t_comment.comment
     */
    private String comment;

    /**
     * 评论人（关联用户ID），对应表字段为：t_comment.author
     */
    private Long author;

    /**
     * 关联ID（可以帖子或者评论），对应表字段为：t_comment.relation_id
     */
    private Long relationId;

    /**
     * 创建人，对应表字段为：t_comment.create_user_id
     */
    private Long createUserId;

    /**
     * 修改人，对应表字段为：t_comment.modified_user_id
     */
    private Long modifiedUserId;

    /**
     * 创建日期，对应表字段为：t_comment.create_date
     */
    private Date createDate;

    /**
     * 修改日期，对应表字段为：t_comment.modified_date
     */
    private Date modifiedDate;

    /**
     * 点赞数量，对应表字段为：t_comment.laud_num
     */
    private Long laudNum;

    /**
     * 对应表字段为：t_comment.deleted
     */
    private Boolean deleted;

    /**
     * 对应表字段为：t_comment.type
     */
    private String type;

    /**
     * 对应表字段为：t_comment.relation_user_id
     */
    private Long relationUserId;

    /**
     * 对应表字段为：t_comment.read_flag
     */
    private Boolean readFlag;
    /**
     * 评论对象标题，对应表字段为：t_comment.relation_title
     */
    private String relationTitle;


    private static final long serialVersionUID = 1L;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getLaudNum() {
        return laudNum;
    }

    public void setLaudNum(Long laudNum) {
        this.laudNum = laudNum;
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

    public String getRelationTitle() {
        return relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }
}