package com.base.service.comment.biz;

import com.base.facade.comment.enums.FieldType;
import com.base.facade.comment.enums.ModuleType;
import com.base.facade.comment.pojo.cond.CommentCond;
import com.base.facade.comment.pojo.po.Comment;
import com.base.facade.comment.pojo.vo.CommentVo;
import com.base.facade.exception.BaseCommonBizException;
import com.base.facade.laud.pojo.po.Laud;
import com.base.service.comment.dao.CommentMapper;
import com.base.service.pool.BaseServiceBizPool;
import com.base.spi.UserLockService;
import com.base.spi.response.CallbackResp;
import com.jucaifu.common.log.LOG;
import com.jucaifu.core.biz.AbsBaseBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by liuxun on 16-10-12.
 */
@Service
public class CommentBiz extends AbsBaseBiz<Comment, CommentVo, CommentMapper> {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public CommentMapper getDefaultMapper() {
        return commentMapper;
    }

    /**
     * 校验评论
     * @param comment
     */
    private void commentFilter(Comment comment){
        if (comment == null){
            throw new BaseCommonBizException("评论对象不能空");
        }
        if (comment.getRelationId() == null){
            throw new BaseCommonBizException("评论关联ID不能为空");
        }
        if (StringUtils.isBlank(comment.getType())){
            throw new BaseCommonBizException("评论类型不能为空");
        }
        if (comment.getAuthor() == null){
            throw new BaseCommonBizException("评论用户不能为空");
        }
        if (StringUtils.isBlank(comment.getComment())){
            throw new BaseCommonBizException("评论内容不能为空");
        }
        if (comment.getRelationUserId() == null){
            throw new BaseCommonBizException("评论关联用户ID不能为空");
        }
        /*if (riskNum != null){
            LOG.i(this,"评论风险校验开始");
            CommentCond cond = new CommentCond();
            cond.setAuthor(comment.getAuthor());
            Long publishNum = countTodayByCondition(cond);
            if (publishNum.intValue() >= riskNum.intValue()){
                // 已达风险控制上限，通知服务调用者结束调用
                CallbackResp resp = service.lockUser(comment.getAuthor());
                throw new BaseCommonBizException(resp.getErrorMsg());
            }
            LOG.i(this,"评论风险校验结束");
        }*/
    }

    /**
     * Save comment boolean.
     *
     * @param comment the comment
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Long saveComment(Comment comment){
        commentFilter(comment);
        comment.setDeleted(Boolean.FALSE);
        comment.setCreateDate(new Date());
        comment.setCreateUserId(comment.getAuthor());
        commentMapper.insert(comment);
        return comment.getId();
    }


    /**
     * Laud comment.
     *
     * @param laud the laud
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void laudComment(Laud laud){
        laud.setType(ModuleType.COMMENT.getCode());
        boolean flag = BaseServiceBizPool.getInstance().laudBiz.saveLaud(laud);
        if (flag){
            Comment comment = commentMapper.select(laud.getRelationId());
            if (comment == null){
                throw new BaseCommonBizException("评论不存在");
            }
            if (comment.getDeleted()){
                //　评论已经被删除
                throw new BaseCommonBizException("评论已经被删除");
            }
            // 更新点赞数量
            commentMapper.addNumByField(laud.getRelationId(), FieldType.LAUD.getCode());
        }
    }

    /**
     * Merge comment.
     *
     * @param sourcesUserId the sources user id
     * @param targetUserId  the target user id
     */
    public void mergeComment(Long sourcesUserId, Long targetUserId){
        commentMapper.mergeComment(sourcesUserId,targetUserId);
    }

    /**
     * Get comment by condition page comment vo.
     *
     * @param vo the vo
     * @return the comment vo
     */
    public CommentVo getCommentByConditionPage(CommentVo vo){
        List<Comment> list = commentMapper.getCommentByConditionPage(vo);
        vo.setResultList(list);
        return vo;
    }

    /**
     * Update read by relation id.
     *
     * @param relationId     the relation id
     * @param type           the type
     * @param relationUserId the relation user id
     */
    public void updateReadByRelationId(Long relationId,String type,Long relationUserId){
        if (relationId == null){
            throw new BaseCommonBizException("评论关联ID不能为空");
        }
        if (type == null){
            throw new BaseCommonBizException("评论类型不能为空");
        }
        if (relationUserId == null){
            throw new BaseCommonBizException("评论关联用户ID不能为空");
        }
        commentMapper.updateReadByRelationId(relationId,type,relationUserId);
    }

    /**
     * Get dynamic by condition page comment vo.
     *
     * @param vo the vo
     * @return the comment vo
     */
    public CommentVo getDynamicByConditionPage(CommentVo vo){

        return this.getCommentByConditionPage(vo);
    }

    /**
     * Count dynamic by condition long.
     *
     * @param cond the cond
     * @return the long
     */
    public Long countDynamicByCondition(CommentCond cond) {

        List<Comment> list = commentMapper.getCommentByCondition(cond);
        return Long.valueOf(list.size());
    }

    /**
     * Count today by condition long.
     *
     * @param cond the cond
     * @return the long
     */
    public Long countTodayByCondition(CommentCond cond) {
        cond.setSysdate(new Date());
        List<Comment> list = commentMapper.getCommentByCondition(cond);
        return Long.valueOf(list.size());
    }

    /**
     * Cancel laud.
     *
     * @param laud the laud
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void cancelLaud(Laud laud) {
        laud.setType(ModuleType.COMMENT.getCode());
        boolean flag = BaseServiceBizPool.getInstance().laudBiz.cancelLaud(laud);
        if (flag){
            // 更新点赞数量
            commentMapper.subNumByField(laud.getRelationId(), FieldType.LAUD.getCode());
        }
    }

    /**
     * Delete comment by emp.
     *
     * @param commentId the comment id
     * @param empId     the emp id
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteCommentByEmp(Long commentId, Long empId) {
        if (commentId == null){
            throw new BaseCommonBizException("评论ID不能为空");
        }
        if (empId == null){
            throw new BaseCommonBizException("员工ID不能为空");
        }
        Comment comment = commentMapper.select(commentId);
        if (comment == null){
            throw new BaseCommonBizException("评论数据不存在");
        }
        if (comment.getDeleted()){
            // 已经删除
            throw new BaseCommonBizException("该数据已经被删除，请不要重复删除");
        }
        comment.setDeleted(Boolean.TRUE);
        comment.setModifiedDate(new Date());
        comment.setModifiedUserId(empId);
        commentMapper.update(comment);
    }

    /**
     * 根据id查询评论信息
     * @param commentId
     * @return
     */
    public Comment detail(Long commentId) {
        if (commentId == null){
            throw new BaseCommonBizException("评论ID不能为空");
        }
        Comment comment = commentMapper.select(commentId);
        if (comment == null){
            throw new BaseCommonBizException("评论数据不存在");
        }
        return comment;
    }
}
