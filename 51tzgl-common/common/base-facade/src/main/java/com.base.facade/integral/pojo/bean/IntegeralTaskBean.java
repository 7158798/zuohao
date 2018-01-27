package com.base.facade.integral.pojo.bean;

//import com.sun.javafx.tk.Toolkit;
import com.base.facade.integral.enums.IntegralType;
import com.base.facade.integral.pojo.po.IntegralTask;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zh on 16-10-23.
 */
public class IntegeralTaskBean {
    private BigDecimal postsAmount;

    private BigDecimal raidersAmount;

    private BigDecimal commentAmount;

    private BigDecimal shareAmount;

    private BigDecimal commentMaxAmount;

    private BigDecimal shareMaxAmount;

    private BigDecimal siginMinAmount;

    private BigDecimal siginMaxAmount;

    private BigDecimal cashMaxAmount;

    private Long employeeid;


    private BigDecimal raidersMaxAmount;

    private BigDecimal postsMaxAmount;

    public BigDecimal getRaidersMaxAmount() {
        return raidersMaxAmount;
    }

    public void setRaidersMaxAmount(BigDecimal raidersMaxAmount) {
        this.raidersMaxAmount = raidersMaxAmount;
    }

    public BigDecimal getPostsMaxAmount() {
        return postsMaxAmount;
    }

    public void setPostsMaxAmount(BigDecimal postsMaxAmount) {
        this.postsMaxAmount = postsMaxAmount;
    }

    public BigDecimal getPostsAmount() {
        return postsAmount;
    }

    public static IntegeralTaskBean convertToBean(List<IntegralTask> list){
        IntegeralTaskBean resp = new IntegeralTaskBean();
        if(list == null){
            return resp;
        }
        for(IntegralTask integralTask:list){
            if(StringUtils.equals(integralTask.getTaskNo(), IntegralType.PUBLISH_RAIDERS.getCode())){
                resp.setRaidersAmount(integralTask.getIntegralAccount());
                resp.setRaidersMaxAmount(integralTask.getDailyLimit());
            }
            if(StringUtils.equals(integralTask.getTaskNo(), IntegralType.PUBLISH_POSTS.getCode())){
                resp.setPostsAmount(integralTask.getIntegralAccount());
                resp.setPostsMaxAmount(integralTask.getDailyLimit());
            }
            if(StringUtils.equals(integralTask.getTaskNo(), IntegralType.COMMONT.getCode())){
                resp.setCommentAmount(integralTask.getIntegralAccount());
                resp.setCommentMaxAmount(integralTask.getDailyLimit());
            }
            if(StringUtils.equals(integralTask.getTaskNo(), IntegralType.SHARE.getCode())){
                resp.setShareAmount(integralTask.getIntegralAccount());
                resp.setShareMaxAmount(integralTask.getDailyLimit());
            }
            if(StringUtils.equals(integralTask.getTaskNo(),IntegralType.SIGIN_ERVERDY.getCode())){
                resp.setSiginMinAmount(integralTask.getIntegralAccount());
                resp.setSiginMaxAmount(integralTask.getDailyLimit());
            }
            if(StringUtils.equals(integralTask.getTaskNo(),IntegralType.CONVERT_CASH.getCode())){
                resp.setCashMaxAmount(integralTask.getIntegralAccount());
            }
        }
        return resp;
    }

    public static List<IntegralTask> converToList(IntegeralTaskBean bean){
        List<IntegralTask> list = new ArrayList<>();
        IntegralTask posts = new IntegralTask();
        posts.setTaskNo(IntegralType.PUBLISH_POSTS.getCode());
        posts.setIntegralAccount(bean.postsAmount);
        posts.setDailyLimit(bean.postsMaxAmount);
        posts.setActive(true);
        posts.setCreateUserId(bean.employeeid);

        IntegralTask raiders = new IntegralTask();
        raiders.setTaskNo(IntegralType.PUBLISH_RAIDERS.getCode());
        raiders.setIntegralAccount(bean.raidersAmount);
        raiders.setDailyLimit(bean.raidersMaxAmount);
        raiders.setActive(true);
        raiders.setCreateUserId(bean.employeeid);

        IntegralTask comment = new IntegralTask();
        comment.setTaskNo(IntegralType.COMMONT.getCode());
        comment.setIntegralAccount(bean.commentAmount);
        comment.setDailyLimit(bean.commentMaxAmount);
        comment.setActive(true);
        comment.setCreateUserId(bean.employeeid);

        IntegralTask share = new IntegralTask();
        share.setTaskNo(IntegralType.SHARE.getCode());
        share.setIntegralAccount(bean.shareAmount);
        share.setDailyLimit(bean.shareMaxAmount);
        share.setActive(true);
        share.setCreateUserId(bean.employeeid);

        IntegralTask sigin = new IntegralTask();
        sigin.setTaskNo(IntegralType.SIGIN_ERVERDY.getCode());
        sigin.setIntegralAccount(bean.siginMinAmount);
        sigin.setDailyLimit(bean.siginMaxAmount);
        sigin.setActive(true);
        sigin.setCreateUserId(bean.employeeid);

        IntegralTask cash = new IntegralTask();
        cash.setTaskNo(IntegralType.CONVERT_CASH.getCode());
        cash.setIntegralAccount(bean.cashMaxAmount);
        cash.setActive(true);
        cash.setCreateUserId(bean.employeeid);


        list.add(posts);
        list.add(raiders);
        list.add(comment);
        list.add(share);
        list.add(sigin);
        list.add(cash);

        return list;


    }

    public Long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Long employeeid) {
        this.employeeid = employeeid;
    }

    public void setPostsAmount(BigDecimal postsAmount) {
        this.postsAmount = postsAmount;
    }

    public BigDecimal getRaidersAmount() {
        return raidersAmount;
    }

    public void setRaidersAmount(BigDecimal raidersAmount) {
        this.raidersAmount = raidersAmount;
    }

    public BigDecimal getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(BigDecimal commentAmount) {
        this.commentAmount = commentAmount;
    }

    public BigDecimal getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(BigDecimal shareAmount) {
        this.shareAmount = shareAmount;
    }

    public BigDecimal getCommentMaxAmount() {
        return commentMaxAmount;
    }

    public void setCommentMaxAmount(BigDecimal commentMaxAmount) {
        this.commentMaxAmount = commentMaxAmount;
    }

    public BigDecimal getShareMaxAmount() {
        return shareMaxAmount;
    }

    public void setShareMaxAmount(BigDecimal shareMaxAmount) {
        this.shareMaxAmount = shareMaxAmount;
    }

    public BigDecimal getSiginMinAmount() {
        return siginMinAmount;
    }

    public void setSiginMinAmount(BigDecimal siginMinAmount) {
        this.siginMinAmount = siginMinAmount;
    }

    public BigDecimal getSiginMaxAmount() {
        return siginMaxAmount;
    }

    public void setSiginMaxAmount(BigDecimal siginMaxAmount) {
        this.siginMaxAmount = siginMaxAmount;
    }

    public BigDecimal getCashMaxAmount() {
        return cashMaxAmount;
    }

    public void setCashMaxAmount(BigDecimal cashMaxAmount) {
        this.cashMaxAmount = cashMaxAmount;
    }
}
