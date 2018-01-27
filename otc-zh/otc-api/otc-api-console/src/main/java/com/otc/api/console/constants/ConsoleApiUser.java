package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by a123 on 17-4-26.
 */
public interface ConsoleApiUser extends ApiBasePathConstant {

    /**
     * 前缀：用户前缀
     */
    String PREFIX_USER_CONSOLE_API = CONSOLE_API+MODULE_USER;

    /**
     * 查询用户列表
     */
    String USER_LIST_QUERY_API = PREFIX_USER_CONSOLE_API+"/list"+PATH_QUERY_JSON;

    /**
     * 禁用用户
     */
    String USER_FORBIDDEN_API = PREFIX_USER_CONSOLE_API+"/forbidden";

    /**
     * 解除禁用
     */
    String USER_RELIEVE_API = PREFIX_USER_CONSOLE_API+"/relieve";

    /**
     * 重置密码
     */
    String USER_RESET_PWD_API = PREFIX_USER_CONSOLE_API+"/resetPwd";

    /**
     * 查询用户操作记录
     */
    String USER_OPERATION_LIST_API = PREFIX_USER_CONSOLE_API+"/operationlist"+PATH_QUERY_JSON;


    /**
     * 查询用户操作记录
     */
    String USER_ASSET_LIST_API = PREFIX_USER_CONSOLE_API+"/assetlist"+PATH_QUERY_JSON;

}
