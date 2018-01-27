package com.otc.core.validator;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaiyz on 15-11-19.
 */
public class SmsCodeBean implements Serializable {

    /**
     * 提供外部删除使用
     */
    private String id;
    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 短信类型
     */
    private SmsType smsType;

    /**
     * 验证码
     */
    private String code;

    /**
     * 发送次数
     */
    private int count;

    /**
     * 是否过期
     */
    private boolean expired;

    /**
     * 生成时间
     */
    private Date createDate;
    private Date updateDate;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsType smsType) {
        this.smsType = smsType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        this.updateDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
