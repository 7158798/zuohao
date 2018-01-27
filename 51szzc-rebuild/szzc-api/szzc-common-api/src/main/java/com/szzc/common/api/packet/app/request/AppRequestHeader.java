package com.szzc.common.api.packet.app.request;

@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)

/**
 * AppRequestHeader
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class AppRequestHeader {


    /**
     * token : 区分用户是否登录的token
     * seqNo : 1
     * reqTimeStamp : 1445236089890
     * deviceInfo : {"deviceType":"1","name":"iPhone6 Plus","idfa":"广告标示符uuid","mac":"14:B4:84:F6:BB:99","deviceId":"","androidId":"","imsi":"国际移动用户识别码","imei":"国际移动电话设备识别码"}
     * appName : jcf
     * appVersion : 1.1
     * appChannel : 应用程序渠道
     * longitude :
     * latitude :
     */

    private String token;
    private int seqNo;
    private long reqTimeStamp;

    private DeviceInfo deviceInfo;

    private String appName;
    private String appVersion;
    private String appChannel;
    private String longitude;
    private String latitude;

    public void setToken(String token) {
        this.token = token;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public void setReqTimeStamp(long reqTimeStamp) {
        this.reqTimeStamp = reqTimeStamp;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getToken() {
        return token;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public long getReqTimeStamp() {
        return reqTimeStamp;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

}
