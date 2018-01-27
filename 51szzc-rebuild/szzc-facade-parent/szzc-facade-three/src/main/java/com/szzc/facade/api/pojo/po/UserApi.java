package com.szzc.facade.api.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class UserApi extends BasePo {

    /**
     * 对应表字段为：t_user_api.user_id
     */
    private Integer userId;

    /**
     * 对应表字段为：t_user_api.api_key
     */
    private String apiKey;

    /**
     * 对应表字段为：t_user_api.secret_key
     */
    private String secretKey;

    /**
     * 对应表字段为：t_user_api.create_date
     */
    private Date createDate;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey == null ? null : apiKey.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}