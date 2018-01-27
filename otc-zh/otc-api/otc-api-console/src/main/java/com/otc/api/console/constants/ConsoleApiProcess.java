package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-5-8.
 */
public interface ConsoleApiProcess extends ApiBasePathConstant {

    // 流程前缀
    String PREFIX_PROCESS_CONSOLE_API = CONSOLE_API + "/process";
    // 流程列表
    String LIST_PROCESS_CONSOLE_API = PREFIX_PROCESS_CONSOLE_API + PATH_LIST + PATH_QUERY_JSON;
    // 更新流程
    String UPDATE_PROCESS_CONSOLE_API = PREFIX_PROCESS_CONSOLE_API + PATH_UPDATE;

    //获取审核流程详情
    String LIST_PROCESS_DETAIL_CONSOLE_API = PREFIX_PROCESS_CONSOLE_API + "/detailByType" + PATH_QUERY_JSON;
    // 流程详情接口
    String DETAIL_PROCESS_CONSOLE_API = PREFIX_PROCESS_CONSOLE_API + "/detail" + PATH_QUERY_JSON;

    /**
     * 获取状态列表
     */
    String TRADE_STATUS_LIST_API = PREFIX_PROCESS_CONSOLE_API+"/statusList"+PATH_QUERY_JSON;
}
