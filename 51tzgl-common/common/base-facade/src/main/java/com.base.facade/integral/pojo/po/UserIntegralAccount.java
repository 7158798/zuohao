package com.base.facade.integral.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserIntegralAccount extends BasePo implements Serializable {

    /**
     * id，对应表字段为：t_user_integral_account.id
     */
    private Long id;

    /**
     * 用户主键，对应表字段为：t_user_integral_account.user_id
     */
    private Long userId;

    /**
     * 积分余额，对应表字段为：t_user_integral_account.usable_amount
     */
    private BigDecimal usableAmount;

    /**
     * 历史积分总额，对应表字段为：t_user_integral_account.total_amount
     */
    private BigDecimal totalAmount;

    /**
     * 标记删除，对应表字段为：t_user_integral_account.deleted
     */
    private Boolean deleted;

    /**
     * 创建时间，对应表字段为：t_user_integral_account.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：t_user_integral_account.modified_date
     */
    private Date modifiedDate;

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

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
}