package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-5-2.
 */
public interface ConsoleApiRecord extends ApiBasePathConstant {


    // 前缀充值列表
    String PREFIX_RECHARGE_CONSOLE_API = CONSOLE_API + "/recharge";
    // 充值列表
    String LIST_RECHARGE_CONSOLE_API = PREFIX_RECHARGE_CONSOLE_API + PATH_LIST + PATH_QUERY_JSON;

    // 提现
    String PREFIX_WITHDRAW_CONSOLE_API = CONSOLE_API + "/withdraw";
    // 审核列表
    String LIST_EXAMINE_WITHDRAW_CONSOLE_API = PREFIX_WITHDRAW_CONSOLE_API + "/examine" + PATH_LIST + PATH_QUERY_JSON;
    // 成功列表
    String LIST_SUCCESS_WITHDRAW_CONSOLE_API = PREFIX_WITHDRAW_CONSOLE_API + "/success" + PATH_LIST + PATH_QUERY_JSON;
    // 锁定
    String LOCK_WITHDRAW_CONSOLE_API = PREFIX_WITHDRAW_CONSOLE_API + "/lock";
    // 解锁锁定
    String UNLOCK_WITHDRAW_CONSOLE_API = PREFIX_WITHDRAW_CONSOLE_API + "/unlock";
    String AUDIT_WITHDRAW_CONSOLE_API = PREFIX_WITHDRAW_CONSOLE_API + "/audit";
    // 详情接口
    String DETAIL_RECORD_CONSOLE_API = PREFIX_RECHARGE_CONSOLE_API + "/record/detail" + PATH_QUERY_JSON;
    // 取消提币
    String CANCEL_RECORD_CONSOLE_API = PREFIX_RECHARGE_CONSOLE_API + "/record/cancel";

            // 状态列表
    String LIST_STATUS_RECORD_CONSOLE_API = CONSOLE_API + "/record/status" + PATH_LIST + PATH_QUERY_JSON;

}
