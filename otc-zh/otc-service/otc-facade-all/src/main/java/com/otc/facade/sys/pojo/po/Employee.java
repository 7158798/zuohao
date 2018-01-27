package com.otc.facade.sys.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class Employee extends BasePo {

    /**
     * 对应表字段为：t_employee.name
     */
    private String name;

    /**
     * 对应表字段为：t_employee.login_name
     */
    private String loginName;

    /**
     * 对应表字段为：t_employee.password
     */
    private String password;

    /**
     * 对应表字段为：t_employee.salt
     */
    private String salt;

    /**
     * 对应表字段为：t_employee.phone_number
     */
    private String phoneNumber;

    /**
     * 对应表字段为：t_employee.deleted
     */
    private Boolean deleted;

    /**
     * 对应表字段为：t_employee.status
     */
    private String status;

    /**
     * 对应表字段为：t_employee.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_employee.create_user
     */
    private Long createUser;

    /**
     * 对应表字段为：t_employee.modified_date
     */
    private Date modifiedDate;

    /**
     * 对应表字段为：t_employee.modified_user
     */
    private Long modifiedUser;

    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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