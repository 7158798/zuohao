package com.jucaifu.common.util;

/**
 * ApiPathHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/4.
 */
public class ApiPathHelper {

    private static final String api_format = "%s/";

    private ApiPathHelper() {
    }

    public static String getApi(String module) {
        return "api-" + module;
    }


}
