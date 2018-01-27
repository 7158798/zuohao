package com.otc.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;
import java.io.Serializable;
import java.util.Date;

public class UserAuthentication extends BasePo implements Serializable {

    /**
     * 主键，对应表字段为：t_user_authentication.id
     */
    private Long id;

    /**
     * 用户主键，对应表字段为：t_user_authentication.user_id
     */
    private Long userId;

    /**
     * 证件类型，对应表字段为：t_user_authentication.passport_type
     */
    private String passportType;

    /**
     * 证件号码，对应表字段为：t_user_authentication.passport_no
     */
    private String passportNo;

    /**
     * 出生日期，对应表字段为：t_user_authentication.birthday
     */
    private Date birthday;

    /**
     * 性别，对应表字段为：t_user_authentication.gender
     */
    private Integer gender;

    /**
     * 实名认证时间，对应表字段为：t_user_authentication.create_date
     */
    private Date createDate;

    /**
     * 身份证正面url，对应表字段为：t_user_authentication.identityUrl_On
     */
    private String identityurlOn;

    /**
     * 身份证正面url，对应表字段为：t_user_authentication.identityUrl_Off
     */
    private String identityurlOff;

    /**
     * 手持身份证url，对应表字段为：t_user_authentication.identityUrl_hold
     */
    private String identityurlHold;

    /**
     * 真实姓名，对应表字段为：t_user_authentication.real_name
     */
    private String realName;

    /**
     * 审核人，对应表字段为：t_user_authentication.auditor_id
     */
    private Long auditorId;

    /**
     * 审核时间，对应表字段为：t_user_authentication.auditor_date
     */
    private Date auditorDate;

    /**
     * 提交kyc认证时间，对应表字段为：t_user_authentication.commit_identity_date
     */
    private Date commitIdentityDate;

    /**
     * kyc认证通过时间，对应表字段为：t_user_authentication.pass_date
     */
    private Date passDate;

    /**
     * 认证状态 00：未实名 01：实名认证 02：已经提交KYC 03：KYC审核通过 04：kyc被拒绝，对应表字段为：t_user_authentication.status
     */
    private String status;

    /**
     * 拒绝原因，对应表字段为：t_user_authentication.rejection_reason
     */
    private String rejectionReason;

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

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType == null ? null : passportType.trim();
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo == null ? null : passportNo.trim();
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIdentityurlOn() {
        return identityurlOn;
    }

    public void setIdentityurlOn(String identityurlOn) {
        this.identityurlOn = identityurlOn == null ? null : identityurlOn.trim();
    }

    public String getIdentityurlOff() {
        return identityurlOff;
    }

    public void setIdentityurlOff(String identityurlOff) {
        this.identityurlOff = identityurlOff == null ? null : identityurlOff.trim();
    }

    public String getIdentityurlHold() {
        return identityurlHold;
    }

    public void setIdentityurlHold(String identityurlHold) {
        this.identityurlHold = identityurlHold == null ? null : identityurlHold.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public Date getAuditorDate() {
        return auditorDate;
    }

    public void setAuditorDate(Date auditorDate) {
        this.auditorDate = auditorDate;
    }

    public Date getCommitIdentityDate() {
        return commitIdentityDate;
    }

    public void setCommitIdentityDate(Date commitIdentityDate) {
        this.commitIdentityDate = commitIdentityDate;
    }

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason == null ? null : rejectionReason.trim();
    }
}