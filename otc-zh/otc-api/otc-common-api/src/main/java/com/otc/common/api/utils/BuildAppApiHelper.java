package com.otc.common.api.utils;

import com.jucaifu.common.enums.EnumDeviceType;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.security.DESedeHelper;
import com.jucaifu.common.security.MD5Helper;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.UrlHelper;
import com.otc.common.api.key.SecureKeyManager;
import com.otc.common.api.packet.app.request.AppApiRequest;
import com.otc.common.api.packet.app.request.AppRequestHeader;
import com.otc.common.api.packet.app.request.DeviceInfo;

/**
 * BuildAppApiHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/3.
 */
public class BuildAppApiHelper {

//////////////////////////////
//// 说明：仅仅为了方便客户端调试
//////////////////////////////

    //统一的密钥key
    private static String KEY = SecureKeyManager.getCommonKey();

    //控制是否开启加密模式
    private static boolean isOpenSecurityMode = true;

    /**
     * Build app api request.
     *
     * @param <T>     the type parameter
     * @param bodyObj the body obj
     * @return the app api request
     */
    public static <T> AppApiRequest buildAppApiRequest(T bodyObj, String token) {
        return buildAppApiRequest(KEY, bodyObj, token);
    }

    /**
     * Build app api request 2 hex str.
     *
     * @param <T>     the type parameter
     * @param bodyObj the body obj
     * @return the string
     */
    public static <T> String buildAppApiRequest2HexStr(T bodyObj, String token) {
        AppApiRequest apiRequest = buildAppApiRequest(bodyObj, token);
        return UrlHelper.encodeObj2HexStr(apiRequest);
    }

    private static <T> AppApiRequest buildAppApiRequest(String key, T bodyObj, String token) {

        AppApiRequest apiRequest = null;

        //构建客户端的请求报文

        //1.header
        AppRequestHeader header = new AppRequestHeader();
        header.setToken(token);
        header.setSeqNo(1);
        header.setReqTimeStamp(System.currentTimeMillis());
        header.setAppName("jucaifu");
        header.setAppVersion("999.0.0");
        header.setAppChannel("AppStore");
        header.setLatitude("");
        header.setLongitude("");
        //header-->deviceInfo
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceType(EnumDeviceType.IOS_MOBILE.getValue());
        deviceInfo.setName("iPhone6s-postman");
        header.setDeviceInfo(deviceInfo);

        //2.body object
        String bodyJsonStr = null;
        if (bodyObj != null) {
            bodyJsonStr = JsonHelper.obj2JsonStr(bodyObj);
            LOG.d(BuildAppApiHelper.class, bodyJsonStr);
        }

        //3.sign
        String sign = null;
        if (bodyJsonStr != null) {
            sign = MD5Helper.encodeMD5Hex(bodyJsonStr);
        }

        //4.构建完整的请求报文

        String encodeBodyJsonStr = null;
        try {
            apiRequest = new AppApiRequest();
            apiRequest.setHeader(header);
            apiRequest.setSign(sign);
            if (isOpenSecurityMode && bodyJsonStr != null) {
                encodeBodyJsonStr = DESedeHelper.encrypt(key, bodyJsonStr);
            } else {
                encodeBodyJsonStr = bodyJsonStr;
            }
            apiRequest.setBody(encodeBodyJsonStr);

            LOG.d(BuildAppApiHelper.class, "App请求报文构建成功:");

        } catch (Exception e) {

            LOG.d(BuildAppApiHelper.class, "App请求报文构建失败。");

            e.printStackTrace();
        }


        return apiRequest;
    }

}
