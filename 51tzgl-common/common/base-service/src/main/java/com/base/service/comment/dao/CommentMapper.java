package com.base.service.comment.dao;


import com.base.facade.comment.pojo.cond.CommentCond;
import com.base.facade.comment.pojo.po.Comment;
import com.base.facade.comment.pojo.vo.CommentVo;
import com.jucaifu.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * The interface Comment mapper.
 */
public interface CommentMapper extends BaseMapper<Comment,CommentVo> {

    /**
     * Add num by field int.
     *
     * @param id    the id
     * @param field the field
     * @return the int
     */
    int addNumByField(@Param("id") Long id, @Param("field") String field);

    /**
     * Gets comment by condition page.
     *
     * @param vo the vo
     * @return the comment by condition page
     */
    List<Comment> getCommentByConditionPage(CommentVo vo);

    /**
     * Merge comment int.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     * @return the int
     */
    int mergeComment(@Param("sourcesUserId") Long sourcesUserId, @Param("targetUserId") Long targetUserId);


    /**
     * Update read by relation id int.
     *
     * @param relationId     the relation id
     * @param type           the type
     * @param relationUserId the relation user id
     * @return the int
     */
    int updateReadByRelationId(@Param("relationId") Long relationId, @Param("type") String type, @Param("relationUserId") Long relationUserId);


    /**
     * Gets comment by condition.
     *
     * @param cond the cond
     * @return the comment by condition
     */
    List<Comment> getCommentByCondition(CommentCond cond);

    /**
     * Sub num by field int.
     *
     * @param id    the id
     * @param field the field
     * @return the int
     */
    int subNumByField(@Param("id") Long id, @Param("field") String field);
}