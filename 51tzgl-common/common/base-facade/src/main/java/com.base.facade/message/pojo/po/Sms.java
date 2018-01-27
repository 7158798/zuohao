package com.base.facade.message.pojo.po;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.pojo.po.BasePo;
import com.base.facade.exception.BaseCommonBizException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

public class Sms extends BasePo {

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

    /**上传excel保存路径**/
    private String url;

    /**上传文件中的，前50个手机号,也可以是输入的50个手机号**/
    private String filePhoneNumber;

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

    /**短信发送返回结果**/
    private String sendResultJson;

    private static final long serialVersionUID = 1L;

    /**发送对象**/
    private List<SmsSendObj> sendObjList;


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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SmsSendObj> getSendObjList() {
        return sendObjList;
    }

    public void setSendObjList(List<SmsSendObj> sendObjList) {
        this.sendObjList = sendObjList;
    }

    public String getSendResultJson() {
        return sendResultJson;
    }

    public void setSendResultJson(String sendResultJson) {
        this.sendResultJson = sendResultJson;
    }

    public String getFilePhoneNumber() {
        return filePhoneNumber;
    }

    public void setFilePhoneNumber(String filePhoneNumber) {
        this.filePhoneNumber = filePhoneNumber;
    }

    //短信发送时,一次发送的号码不能超过200
    public List<String> convertList(List<SmsSendObj> sendObjList) {
        List<String> list = new ArrayList<String>();
        if (sendObjList == null || sendObjList.size() == 0) {
            throw new BaseCommonBizException("参数错误,数据转换时,list不能为空");
        }
        LOG.i(this, "size = " + sendObjList.size());
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for (SmsSendObj obj : sendObjList) {
            index++;
            if (StringUtils.isNoneBlank(obj.getSendPhone())) {
                builder.append(obj.getSendPhone() + ",");
            }
            if (index % 200 == 0) {
                String phoneNumber = builder.toString();
                if (StringUtils.isNoneBlank(phoneNumber)) {
                    phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                    //字符拼接后添加进集合,并清空字符对象
                    list.add(phoneNumber);
                }
                builder.setLength(0);
            }
        }

        //将最后取模后，没有放进list的部分，填充进去
        String phoneNumber = builder.toString();
        if (StringUtils.isNoneBlank(phoneNumber)) {
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
            //字符拼接后添加进集合,并清空字符对象
            list.add(phoneNumber);
        }

        return list;
    }
}