package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class EmployeeRole extends BasePo implements Serializable {

    /**
     * 对应表字段为：t_employee_role.employee_id
     */
    private Long employeeId;

    /**
     * 对应表字段为：t_employee_role.role_id
     */
    private Long roleId;

    /**
     * 对应表字段为：t_employee_role.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_employee_role.deleted
     */
    private Boolean deleted;

    /**
     * 对应表字段为：t_employee_role.create_user
     */
    private Long createUser;

    /**
     * 对应表字段为：t_employee_role.modified_date
     */
    private Date modifiedDate;

    /**
     * 对应表字段为：t_employee_role.modified_user
     */
    private Long modifiedUser;

    private static final long serialVersionUID = 1L;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
}