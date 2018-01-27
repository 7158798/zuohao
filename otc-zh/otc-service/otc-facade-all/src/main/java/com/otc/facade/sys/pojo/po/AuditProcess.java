package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class AuditProcess extends BasePo {

    /**
     * 操作类型，对应表字段为：t_audit_process.type
     */
    private String type;

    /**
     * 一级审核角色Id，对应表字段为：t_audit_process.role_id1
     */
    private Long roleId1;

    /**
     * 二级审核角色Id，对应表字段为：t_audit_process.role_id2
     */
    private Long roleId2;

    /**
     * 三级审核角色Id，对应表字段为：t_audit_process.role_id3
     */
    private Long roleId3;

    /**
     * 是否需要输入密码，对应表字段为：t_audit_process.is_need_pwd
     */
    private Boolean isNeedPwd;

    /**
     * 上次更新时间，对应表字段为：t_audit_process.modified_date
     */
    private Date modifiedDate;

    /**
     * 修改人id，对应表字段为：t_audit_process.modified_id
     */
    private Long modifiedId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getRoleId1() {
        return roleId1;
    }

    public void setRoleId1(Long roleId1) {
        this.roleId1 = roleId1;
    }

    public Long getRoleId2() {
        return roleId2;
    }

    public void setRoleId2(Long roleId2) {
        this.roleId2 = roleId2;
    }

    public Long getRoleId3() {
        return roleId3;
    }

    public void setRoleId3(Long roleId3) {
        this.roleId3 = roleId3;
    }

    public Boolean getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(Boolean isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(Long modifiedId) {
        this.modifiedId = modifiedId;
    }
}