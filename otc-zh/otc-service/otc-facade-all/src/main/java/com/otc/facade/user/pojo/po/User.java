package com.otc.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.util.Date;

public class User extends BasePo implements Serializable {


    /**
     * 手机号码，对应表字段为：t_user.mobile_number
     */
    private String mobileNumber;

    /**
     * 登录名，对应表字段为：t_user.login_name
     */
    private String loginName;

    /**
     * 出生日期，对应表字段为：t_user.birthday
     */
    private Date birthday;

    /**
     * 性别，对应表字段为：t_user.gender
     */
    private Integer gender;

    /**
     * 地区，对应表字段为：t_user.region
     */
    private String region;

    /**
     * 密码，对应表字段为：t_user.login_password
     */
    private String loginPassword;

    /**
     * 昵称，对应表字段为：t_user.nice_name
     */
    private String niceName;

    /**
     * 头像，对应表字段为：t_user.avatar
     */
    private String avatar;

    /**
     * 个性签名，对应表字段为：t_user.signature
     */
    private String signature;

    /**
     * 邮箱地址，对应表字段为：t_user.email_address
     */
    private String emailAddress;

    /**
     * 登录时间，对应表字段为：t_user.login_time
     */
    private Date loginTime;

    /**
     * 登录IP，对应表字段为：t_user.login_ip
     */
    private String loginIp;

    /**
     * 连续登录失败次数，对应表字段为：t_user.failed_login_attempts
     */
    private Integer failedLoginAttempts;

    /**
     * 最后登录时间，对应表字段为：t_user.last_login_time
     */
    private Date lastLoginTime;

    /**
     * 逻辑删除标识，对应表字段为：t_user.deleted
     */
    private Boolean deleted;

    /**
     * 注册时间，对应表字段为：t_user.create_date
     */
    private Date createDate;

    /**
     * 修改时间，对应表字段为：t_user.modified_date
     */
    private Date modifiedDate;

    /**
     * 防钓鱼码，对应表字段为：t_user.prevent_fish
     */
    private String preventFish;

    /**
     * 用户状态 01：正常 02：禁用 03：冻结，对应表字段为：t_user.status
     */
    private String status;

    /**
     * 注册IP，对应表字段为：t_user.register_ip
     */
    private String registerIp;

    private static final long serialVersionUID = 1L;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName == null ? null : niceName.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public String getPreventFish() {
        return preventFish;
    }

    public void setPreventFish(String preventFish) {
        this.preventFish = preventFish == null ? null : preventFish.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp == null ? null : registerIp.trim();
    }
}