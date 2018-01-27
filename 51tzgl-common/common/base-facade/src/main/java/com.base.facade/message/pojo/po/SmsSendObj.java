package com.base.facade.message.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.io.Serializable;
import java.util.Date;

public class SmsSendObj extends BasePo {


    /**
     * 短信标识，对应表字段为：t_sms_send_obj.sms_id
     */
    private Long smsId;

    /**
     * 发送对象，对应表字段为：t_sms_send_obj.send_phone
     */
    private String sendPhone;

    private String sendUserName;

    /**
     * 删除标识，对应表字段为：t_sms_send_obj.deleted
     */
    private Boolean deleted;

    /**
     * 创建时间，对应表字段为：t_sms_send_obj.create_date
     */
    private Date createDate;

    /**
     * 创建人员id，对应表字段为：t_sms_send_obj.create_user_id
     */
    private Long createUserId;

    /**
     * 修改时间，对应表字段为：t_sms_send_obj.modified_date
     */
    private Date modifiedDate;

    /**
     * 修改人员id，对应表字段为：t_sms_send_obj.modified_user_id
     */
    private Long modifiedUserId;

    private static final long serialVersionUID = 1L;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
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

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}