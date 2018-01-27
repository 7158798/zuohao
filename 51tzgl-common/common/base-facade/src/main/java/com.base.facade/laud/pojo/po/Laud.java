package com.base.facade.laud.pojo.po;

import com.jucaifu.common.pojo.po.BasePo;

import java.util.Date;

public class Laud extends BasePo {

    /**
     * 对应表字段为：t_laud.relation_id
     */
    private Long relationId;

    /**
     * 对应表字段为：t_laud.laud_user_id
     */
    private Long laudUserId;

    /**
     * 对应表字段为：t_laud.mac_address
     */
    private String macAddress;

    /**
     * 对应表字段为：t_laud.ip_address
     */
    private String ipAddress;

    /**
     * 对应表字段为：t_laud.create_user_id
     */
    private Long createUserId;

    /**
     * 对应表字段为：t_laud.modified_user_id
     */
    private Long modifiedUserId;

    /**
     * 对应表字段为：t_laud.create_date
     */
    private Date createDate;

    /**
     * 对应表字段为：t_laud.modified_date
     */
    private Date modifiedDate;

    /**
     * 对应表字段为：t_laud.deleted
     */
    private Boolean deleted;

    /**
     * 对应表字段为：t_laud.type
     */
    private String type;

    private static final long serialVersionUID = 1L;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress == null ? null : macAddress.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Long modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getLaudUserId() {
        return laudUserId;
    }

    public void setLaudUserId(Long laudUserId) {
        this.laudUserId = laudUserId;
    }
}