package com.base.facade.integral.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IntegralTask extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_integral_task.id
     */
    private Long id;

    /**
     * 任务名称，对应表字段为：t_integral_task.task_name
     */
    private String taskName;

    /**
     * 任务编号，对应表字段为：t_integral_task.task_no
     */
    private String taskNo;

    /**
     * 积分数量，对应表字段为：t_integral_task.integral_account
     */
    private BigDecimal integralAccount;

    /**
     * 每日上限，对应表字段为：t_integral_task.daily_limit
     */
    private BigDecimal dailyLimit;

    /**
     * 是否启用，对应表字段为：t_integral_task.active
     */
    private Boolean active;

    /**
     * 删除标识，对应表字段为：t_integral_task.deleted
     */
    private Boolean deleted;

    /**
     * 创建人，对应表字段为：t_integral_task.create_user_id
     */
    private Long createUserId;

    /**
     * 创建时间，对应表字段为：t_integral_task.create_date
     */
    private Date createDate;

    /**
     * 修改人，对应表字段为：t_integral_task.modified_user_id
     */
    private Long modifiedUserId;

    /**
     * 修改时间，对应表字段为：t_integral_task.modified_date
     */
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public BigDecimal getIntegralAccount() {
        return integralAccount;
    }

    public void setIntegralAccount(BigDecimal integralAccount) {
        this.integralAccount = integralAccount;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}