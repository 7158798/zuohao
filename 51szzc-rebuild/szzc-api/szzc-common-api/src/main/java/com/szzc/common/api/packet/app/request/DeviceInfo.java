package com.szzc.common.api.packet.app.request;

/**
 * DeviceInfo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class DeviceInfo {
    /**
     * deviceType : 1
     * name : iPhone6 Plus
     * idfa : 广告标示符uuid
     * mac : 14:B4:84:F6:BB:99
     * deviceId :
     * androidId :
     * imsi : 国际移动用户识别码
     * imei : 国际移动电话设备识别码
     */

    private String deviceType;
    private String name;
    private String idfa;
    private String mac;
    private String deviceId;
    private String androidId;
    private String imsi;
    private String imei;

    /**
     * Sets device type.
     *
     * @param deviceType the device type
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets idfa.
     *
     * @param idfa the idfa
     */
    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    /**
     * Sets mac.
     *
     * @param mac the mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * Sets device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Sets android id.
     *
     * @param androidId the android id
     */
    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    /**
     * Sets imsi.
     *
     * @param imsi the imsi
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * Sets imei.
     *
     * @param imei the imei
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * Gets device type.
     *
     * @return the device type
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets idfa.
     *
     * @return the idfa
     */
    public String getIdfa() {
        return idfa;
    }

    /**
     * Gets mac.
     *
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * Gets device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Gets android id.
     *
     * @return the android id
     */
    public String getAndroidId() {
        return androidId;
    }

    /**
     * Gets imsi.
     *
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Gets imei.
     *
     * @return the imei
     */
    public String getImei() {
        return imei;
    }
}
