package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class Role extends BasePo {

    /**
     * 角色名称，对应表字段为：t_role.role_name
     */
    private String roleName;

    /**
     * 角色描述，对应表字段为：t_role.description
     */
    private String description;

    /**
     * 角色标识，对应表字段为：t_role.role_mark
     */
    private String roleMark;

    /**
     * 删除标识，对应表字段为：t_role.deleted
     */
    private Boolean deleted;

    /**
     * 创建时间，对应表字段为：t_role.create_date
     */
    private Date createDate;

    /**
     * 创建人员id，对应表字段为：t_role.create_user_id
     */
    private Long createUserId;

    /**
     * 修改时间，对应表字段为：t_role.modified_date
     */
    private Date modifiedDate;

    /**
     * 修改人员，对应表字段为：t_role.modified_user_id
     */
    private Long modifiedUserId;

    private static final long serialVersionUID = 1L;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRoleMark() {
        return roleMark;
    }

    public void setRoleMark(String roleMark) {
        this.roleMark = roleMark == null ? null : roleMark.trim();
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }
}