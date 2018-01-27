package com.base.service.comment.impl;


import com.base.facade.comment.pojo.cond.CommentCond;
import com.base.facade.comment.pojo.po.Comment;
import com.base.facade.comment.pojo.vo.CommentVo;
import com.base.facade.comment.service.CommentFacade;
import com.base.facade.laud.pojo.po.Laud;
import com.base.service.pool.BaseServiceBizPool;
import com.base.spi.UserLockService;

/**
 * Created by liuxun on 16-10-13.
 */
public class CommentFacadeImpl implements CommentFacade {

    @Override
    public void laudComment(Laud laud) {
        BaseServiceBizPool.getInstance().commentBiz.laudComment(laud);
    }

    @Override
    public CommentVo getByConditionPage(CommentVo vo) {
        return BaseServiceBizPool.getInstance().commentBiz.getCommentByConditionPage(vo);
    }

    @Override
    public CommentVo getDynamicByConditionPage(CommentVo vo) {
        return BaseServiceBizPool.getInstance().commentBiz.getDynamicByConditionPage(vo);
    }

    @Override
    public Long countDynamicByCondition(CommentCond cond) {
        return BaseServiceBizPool.getInstance().commentBiz.countDynamicByCondition(cond);
    }

    @Override
    public Long countTodayByCondition(CommentCond cond) {
        return BaseServiceBizPool.getInstance().commentBiz.countTodayByCondition(cond);
    }

    @Override
    public Long saveComment(Comment comment) {
        return BaseServiceBizPool.getInstance().commentBiz.saveComment(comment);
    }

    @Override
    public void updateReadByRelationId(Long relationId, String type, Long relationUserId) {
        BaseServiceBizPool.getInstance().commentBiz.updateReadByRelationId(relationId,type,relationUserId);
    }

    @Override
    public void mergeComment(Long sourcesUserId, Long targetUserId) {
        BaseServiceBizPool.getInstance().commentBiz.mergeComment(sourcesUserId,targetUserId);
    }

    @Override
    public void cancelLaud(Laud laud) {
        BaseServiceBizPool.getInstance().commentBiz.cancelLaud(laud);
    }

    @Override
    public void deleteCommentByEmp(Long commentId, Long empId) {
        BaseServiceBizPool.getInstance().commentBiz.deleteCommentByEmp(commentId, empId);
    }

    @Override
    public Comment detail(Long commentId) {
        return BaseServiceBizPool.getInstance().commentBiz.detail(commentId);
    }
}
