package com.base.facade.message.pojo.vo;

import com.jucaifu.common.pojo.po.BasePo;
import com.jucaifu.common.pojo.vo.BasePageVo;

import java.util.Date;

public class SmsVo extends BasePageVo {

    /**
     * 短信标题，对应表字段为：t_sms.title
     */
    private String title;

    /**
     * 短信内容，对应表字段为：t_sms.content
     */
    private String content;

    /**
     * 发送人数，对应表字段为：t_sms.person_count
     */
    private Integer personCount;

    /**
     * 发送类型：1单个，0批量，对应表字段为：t_sms.sms_type
     */
    private Integer smsType;

    /**
     * 是否立即发送，对应表字段为：t_sms.send_now
     */
    private Integer sendNow;

    /**
     * 定时发送时间，对应表字段为：t_sms.sending_time
     */
    private Date sendingTime;

    /**
     * 实际发送时间，对应表字段为：t_sms.actual_sending_time
     */
    private Date actualSendingTime;

    /**
     * 状态，对应表字段为：t_sms.status
     */
    private Integer status;

    /**
     * 删除标识，对应表字段为：t_sms.deleted
     */
    private Boolean deleted;

    /**
     * 创建时间，对应表字段为：t_sms.create_date
     */
    private Date createDate;

    /**
     * 创建人员id，对应表字段为：t_sms.create_user_id
     */
    private Long createUserId;

    /**
     * 修改时间，对应表字段为：t_sms.modified_date
     */
    private Date modifiedDate;

    /**
     * 修改人员id，对应表字段为：t_sms.modified_user_id
     */
    private Long modifiedUserId;

    private static final long serialVersionUID = 1L;

    /**开始时间**/
    private Date startDate;

    /**结束时间**/
    private Date endDate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public Integer getSendNow() {
        return sendNow;
    }

    public void setSendNow(Integer sendNow) {
        this.sendNow = sendNow;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Date getActualSendingTime() {
        return actualSendingTime;
    }

    public void setActualSendingTime(Date actualSendingTime) {
        this.actualSendingTime = actualSendingTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}