package com.base.facade.comment.pojo.poex;

import java.io.Serializable;

/**
 * Created by lx on 16-11-3.
 */
public class CommentInfo implements Serializable {

    private Long relationId;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
