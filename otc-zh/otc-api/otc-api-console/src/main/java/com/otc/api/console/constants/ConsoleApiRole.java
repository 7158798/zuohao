package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-21.
 */
public interface ConsoleApiRole extends ApiBasePathConstant {

    /**
     * 前缀：权限
     */
    String PREFIX_ROLE_CONSOLE_API = CONSOLE_API + "/role";
    // 新增角色
    String ADD_ROLE_CONSOLE_API = PREFIX_ROLE_CONSOLE_API + PATH_ADD;
    // 角色列表
    String LIST_ROLE_CONSOLE_API = PREFIX_ROLE_CONSOLE_API + PATH_LIST + PATH_QUERY_JSON;
    // 更新角色
    String UPDATE_ROLE_CONSOLE_API = PREFIX_ROLE_CONSOLE_API + PATH_UPLOAD;
    // 角色详细信息
    String DETAIL_ROLE_CONSOLE_API = PREFIX_ROLE_CONSOLE_API + "/detail" + PATH_QUERY_JSON;

    /**
     * 查询权限列表
     */
    String LIST_PERMISSION_CONSOLE_API = CONSOLE_API + MODULE_PERMISSION + "/permisslist" + PATH_QUERY_JSON;

    // 角色权限列表
    String LIST_ROLR_PERMISSION_CONSOLE_API = CONSOLE_API + MODULE_PERMISSION_ROLE  + PATH_QUERY_JSON;

}
