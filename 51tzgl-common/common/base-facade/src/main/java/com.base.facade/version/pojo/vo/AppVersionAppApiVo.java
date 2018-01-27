/** 
 * Creation Date:2015年9月10日下午12:55:12 
 * Copyright (c) 2015, 上海佐昊网络科技有限公司 All Rights Reserved. 
 */
package com.base.facade.version.pojo.vo;


import com.jucaifu.common.pojo.vo.BasePageVo;

/**
 * 数据库查询参数bean
 * 
 * @author zhijun
 */
public class AppVersionAppApiVo extends BasePageVo {
    private String appName;
    private String deviceType;
    private String version;
    private Boolean active;
    private Boolean forceGoweb;

    public AppVersionAppApiVo() {
    }

    public AppVersionAppApiVo(Boolean active) {
        this.active = active;
    }

    public AppVersionAppApiVo(String appName, String deviceType) {
        this.appName = appName;
        this.deviceType = deviceType;
    }

    public AppVersionAppApiVo(String appName, String deviceType, Boolean active) {
        this.appName = appName;
        this.deviceType = deviceType;
        this.active = active;
    }

    public AppVersionAppApiVo(String appName, String deviceType, String version, Boolean active) {
        this.appName = appName;
        this.deviceType = deviceType;
        this.version = version;
        this.active = active;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getForceGoweb() {
        return forceGoweb;
    }

    public void setForceGoweb(Boolean forceGoweb) {
        this.forceGoweb = forceGoweb;
    }
}
