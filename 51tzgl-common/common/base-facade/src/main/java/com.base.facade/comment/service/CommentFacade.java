package com.base.facade.comment.service;

import com.base.facade.comment.pojo.cond.CommentCond;
import com.base.facade.comment.pojo.po.Comment;
import com.base.facade.comment.pojo.vo.CommentVo;
import com.base.facade.laud.pojo.po.Laud;
import com.base.spi.UserLockService;

/**
 * Created by liuxun on 16-10-13.
 */
public interface CommentFacade {

    /**
     * Laud comment.
     *
     * @param laud the laud
     */
    void laudComment(Laud laud);

    /**
     * Gets by condition page.
     *
     * @param vo the vo
     * @return the by condition page
     */
    CommentVo getByConditionPage(CommentVo vo);

    /**
     * Gets dynamic by condition page.
     *
     * @param vo the vo
     * @return the dynamic by condition page
     */
    CommentVo getDynamicByConditionPage(CommentVo vo);

    /**
     * Count dynamic by condition long.
     *
     * @param cond the cond
     * @return the long
     */
    Long countDynamicByCondition(CommentCond cond);

    /**
     * Count today by condition long.
     *
     * @param cond the cond
     * @return the long
     */
    Long countTodayByCondition(CommentCond cond);

    /**
     * Save comment long.
     *
     * @param comment the comment
     * @return the long
     */
    Long saveComment(Comment comment);

    /**
     * Update read by relation id.
     *
     * @param relationId     the relation id
     * @param type           the type
     * @param relationUserId the relation user id
     */
    void updateReadByRelationId(Long relationId,String type,Long relationUserId);


    /**
     * Merge comment.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     */
    void mergeComment(Long sourcesUserId, Long targetUserId);

    /**
     * 取消点赞（评论）
     * @param laud
     */
    void cancelLaud(Laud laud);
    /**
     * 后台员工删除评论
     * @param commentId 评论id
     * @param empId　　　员工id
     */
    void deleteCommentByEmp(Long commentId,Long empId);

    /**
     * 根据id查询评论信息
     * @param commentId 主键id
     * @return
     */
    Comment detail(Long commentId);
}
