package com.base.facade.weixin.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class WeixinLog extends BasePo {

    /**
     * 对应表字段为：t_weixin_log.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_weixin_log.modified_date
     */
    private Date modifiedDate;

    /**
     * 对应表字段为：t_weixin_log.request
     */
    private String request;

    /**
     * 对应表字段为：t_weixin_log.response
     */
    private String response;

    /**
     * 操作是否成功，对应表字段为：t_weixin_log.status
     */
    private boolean status;

    /**
     * 对应表字段为：t_weixin_log.request_url
     */
    private String requestUrl;

    /**
     * 对应表字段为：t_weixin_log.description
     */
    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}