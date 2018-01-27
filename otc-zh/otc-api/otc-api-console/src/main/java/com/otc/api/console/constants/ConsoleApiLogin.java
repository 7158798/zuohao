package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zhaiyz on 15-11-3.
 */
public interface ConsoleApiLogin extends ApiBasePathConstant {

    /**
     * 前缀
     */
    String API_PREFIX = CONSOLE_API;

    /**
     * 登录
     */
    String LOGIN_API = API_PREFIX + "/login";

    /**
     * 退出
     */
    String LOGOUT_API = API_PREFIX + "/logout" + PATH_QUERY_JSON;
}
