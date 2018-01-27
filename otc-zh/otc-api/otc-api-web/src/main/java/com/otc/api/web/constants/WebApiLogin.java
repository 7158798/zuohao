package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-25.
 */
public interface WebApiLogin extends ApiBasePathConstant {

    /**
     * 用户登录
     */
    String PREFIX_WEB_API = WEB_API + MODULE_USER;

    /**
     * 获取图形验证码
     */
    String WEB_VALIDATE_API = PREFIX_WEB_API+"/"+ PATH_QUERY_JSON;
}
