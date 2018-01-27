package com.jucaifu.common.configs;

import javax.servlet.http.HttpServletRequest;

import com.jucaifu.common.log.LOG;

/**
 * ApiVersionManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/19.
 */
public interface ApiVersionManager {

    //////////////////////////////
    ////需要更新的版本号
    //////////////////////////////
    String V = ApiVersionManager.V_ONLINE;//生产
//    String V = ApiVersionManager.V_DEV;//开发

//    String V = getApiVersion();

    /**
     * 开发环境对应的版本
     */
    String V_DEV = "055";

    /**
     * 生产对应的版本
     */
    String V_ONLINE = "";


//    /**
//     * Gets api version.
//     *
//     * @return the api version
//     */
//    static String getApiVersion() {
//
//        if (GlobalConfig.JCF_IS_ONLINE) {
//            return V_ONLINE;
//        } else {
//            return V_DEV;
//        }
//    }

    /**
     * Validate api version.
     *
     * @param version the version
     * @return the boolean
     */
    static boolean validateApiVersion(String version) {

        boolean result = false;

        if (version != null) {
            result = version.equals(V);
        }

        return result;
    }


    /**
     * Gets api version.
     *
     * @param apiType    the api type
     * @param requestUri the request uri
     * @return the api version
     */
    static String getApiVersion(String apiType, String requestUri) {

        String apiVersion = null;

        try {
            String[] paths = requestUri.split("\\/");

            String currentVersion = paths[2];

            apiVersion = currentVersion.substring(apiType.length());

        } catch (Exception e) {
            LOG.e("", "getApiVersion", e);
        }

        return apiVersion;
    }

    /**
     * Validate api version.
     *
     * @param apiType the api type
     * @param request the request
     * @return the boolean
     */
    static boolean validateApiVersion(String apiType, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String version = getApiVersion(apiType, requestUri);
        return validateApiVersion(version);
    }

}
