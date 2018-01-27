package com.base.facade.integral.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserIntegralDetail extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_user_integral_detail.id
     */
    private Long id;

    /**
     * 用户主键，对应表字段为：t_user_integral_detail.user_id
     */
    private Long userId;

    /**
     * 积分类型，对应表字段为：t_user_integral_detail.type
     */
    private Integer type;

    /**
     * 0:支出；1:收入，对应表字段为：t_user_integral_detail.operate_type
     */
    private Integer operateType;

    /**
     * 操作积分值，对应表字段为：t_user_integral_detail.operate_amount
     */
    private BigDecimal operateAmount;

    /**
     * 变化前值，对应表字段为：t_user_integral_detail.before_amount
     */
    private BigDecimal beforeAmount;

    /**
     * 变化后值，对应表字段为：t_user_integral_detail.after_amount
     */
    private BigDecimal afterAmount;

    /**
     * 备注，对应表字段为：t_user_integral_detail.remark
     */
    private String remark;

    /**
     * 创建时间，对应表字段为：t_user_integral_detail.create_date
     */
    private Date createDate;

    /**
     * 删除标识，对应表字段为：t_user_integral_detail.deleted
     */
    private Boolean deleted;


    private Long relationId;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public BigDecimal getOperateAmount() {
        return operateAmount;
    }

    public void setOperateAmount(BigDecimal operateAmount) {
        this.operateAmount = operateAmount;
    }

    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}