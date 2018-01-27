package com.otc.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class UserOperation extends BasePo implements Serializable {

    /**
     * 操作id，对应表字段为：t_user_operation.id
     */
    private Long id;

    /**
     * 用户id，对应表字段为：t_user_operation.user_id
     */
    private Long userId;

    /**
     * 操作类型，对应表字段为：t_user_operation.operation_type
     */
    private String operationType;

    /**
     * 创建时间，对应表字段为：t_user_operation.create_date
     */
    private Date createDate;

    /**
     * 操作IP，对应表字段为：t_user_operation.login_ip
     */
    private String loginIp;

    /**
     * 所在区域，对应表字段为：t_user_operation.login_adress
     */
    private String loginAdress;

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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getLoginAdress() {
        return loginAdress;
    }

    public void setLoginAdress(String loginAdress) {
        this.loginAdress = loginAdress == null ? null : loginAdress.trim();
    }
}