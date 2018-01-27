package com.base.facade.user.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class UserLogin  extends BasePo implements Serializable {
    

    /**
     * 用户id，对应表字段为：t_user_login.user_id
     */
    private Long userId;

    /**
     * 登录时间，对应表字段为：t_user_login.login_date
     */
    private Date loginDate;

    /**
     * 登录IP，对应表字段为：t_user_login.login_ip
     */
    private String loginIp;

    /**
     * 登录平台，对应表字段为：t_user_login.login_platform
     */
    private String loginPlatform;

    /**
     * 登录状态 S:成功 F：失败，对应表字段为：t_user_login.login_status
     */
    private String loginStatus;


    /**
     * 逻辑删除标识，对应表字段为：t_user_login.deleted
     */
    private Boolean deleted;

    /**
     * 创建时间，对应表字段为：t_user_login.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getLoginPlatform() {
        return loginPlatform;
    }

    public void setLoginPlatform(String loginPlatform) {
        this.loginPlatform = loginPlatform == null ? null : loginPlatform.trim();
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
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

}