package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class RoleResource extends BasePo implements Serializable {

    /**
     * 对应表字段为：t_role_resource.role_id
     */
    private Long roleId;

    /**
     * 对应表字段为：t_role_resource.resource_id
     */
    private Long resourceId;

    /**
     * 对应表字段为：t_role_resource.deleted
     */
    private Boolean deleted;

    /**
     * 对应表字段为：t_role_resource.create_user
     */
    private Long createUser;

    /**
     * 对应表字段为：t_role_resource.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_role_resource.modified_user
     */
    private Long modifiedUser;

    /**
     * 对应表字段为：t_role_resource.modified_date
     */
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}