package com.otc.api.console.utils;

/**
 * Created by yong on 15-12-14.
 */
public class UserAgent {

    private String browserType;//浏览器类型
    private String browserVersion;//浏览器版本
    private String platformType;//平台类型
    private String platformSeries;//平台系列
    private String platformVersion;//平台版本

    public UserAgent(String browserType, String browserVersion,
                     String platformType, String platformSeries, String platformVersion) {
        this.browserType = browserType;
        this.browserVersion = browserVersion;
        this.platformType = platformType;
        this.platformSeries = platformSeries;
        this.platformVersion = platformVersion;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getPlatformSeries() {
        return platformSeries;
    }

    public void setPlatformSeries(String platformSeries) {
        this.platformSeries = platformSeries;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }
}
