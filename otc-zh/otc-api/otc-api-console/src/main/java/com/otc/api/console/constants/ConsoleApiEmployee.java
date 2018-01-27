package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zhaiyz on 15-12-3.
 */
public interface ConsoleApiEmployee extends ApiBasePathConstant {

    /**
     * 用户管理Api前缀
     */
    String PREFIX_EMPLOYEE_API = CONSOLE_API + MODULE_PERMISSION_SYSUSER;

    /**
     * 添加用户
     */
    String ADD_EMPLOYEE_API = PREFIX_EMPLOYEE_API + PATH_ADD;

    /**
     * 编辑用户
     */
    String UPDATE_EMPLOYEE_API = PREFIX_EMPLOYEE_API + PATH_UPDATE;

    /**
     * 查询用户列表
     */
    String LIST_EMPLOYEE_API = PREFIX_EMPLOYEE_API + PATH_LIST + PATH_QUERY_JSON;

    /**
     * 用户启用停用
     */
    String SWITCH_EMPLOYEE_API = PREFIX_EMPLOYEE_API + "/switch";

    /**
     * 用户编辑页面详情
     */
    String EDIT_EMPLOYEE_DETAIL_API = PREFIX_EMPLOYEE_API + "/editdetail" + PATH_QUERY_JSON;

    /**
     * 修改用户密码
     */
    String CHANGE_EMPLOYEE_PASSWORD_API = PREFIX_EMPLOYEE_API + "/setpwd";

    /**
     * 给员工分配角色
     */
    String ALLOT_EMPLOYEE_ROLE_API = PREFIX_EMPLOYEE_API + "/allotroles";

    /**
     * 查询用户详情
     */
    String DETAIL_EMPLOYEE_API = PREFIX_EMPLOYEE_API + "/detail" + PATH_QUERY_JSON;

    /**
     * 员工自己修改密码
     */
    String UPDATE_PASSWORD_SELF_API = PREFIX_EMPLOYEE_API + "/updatepwd";

    /**
     * 员工自己修改手机号
     */
    String UPDATE_PHONE_NUMBER_API = PREFIX_EMPLOYEE_API + "/updatephone";

    /**
     * 员工自己修改手机号
     */
    String UPDATE_EMAIL_API = PREFIX_EMPLOYEE_API + "/updateemail";

    /**
     * 获取用户详情
     */
    String DETAIL_EMPLOYEE_SELF_API = PREFIX_EMPLOYEE_API + "/userdetail" + PATH_QUERY_JSON;
}
