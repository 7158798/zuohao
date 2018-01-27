package com.base.facade.version.pojo.po;

import java.util.Date;

import com.jucaifu.common.pojo.po.BasePo;

public class MobileVersion extends BasePo{

    /**
     * App名称，对应表字段为：t_app_version.app_name
     */
    private String appName;

    /**
     * 设备类型，对应表字段为：t_app_version.device_type
     */
    private String deviceType;

    /**
     * 版本，对应表字段为：t_app_version.version
     */
    private String version;

    /**
     * 下载地址，对应表字段为：t_app_version.download_url
     */
    private String downloadUrl;

    /**
     * 更新日期，对应表字段为：t_app_version.update_date
     */
    private Date updateDate;

    /**
     * 更新内容，对应表字段为：t_app_version.update_info
     */
    private String updateInfo;

    /**
     * 是否强制去网站，优先级高于强制升级，对应表字段为：t_app_version.force_goweb
     */
    private Boolean forceGoweb;

    /**
     * 是否起效，对应表字段为：t_app_version.active
     */
    private Boolean active;

    /**
     * 备注，对应表字段为：t_app_version.remark
     */
    private String remark;

    /**
     * 创建日期，对应表字段为：t_app_version.create_date
     */
    private Date createDate;

    /**
     * 修改日期，对应表字段为：t_app_version.modified_date
     */
    private Date modifiedDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo == null ? null : updateInfo.trim();
    }

    public Boolean getForceGoweb() {
        return forceGoweb;
    }

    public void setForceGoweb(Boolean forceGoweb) {
        this.forceGoweb = forceGoweb;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}