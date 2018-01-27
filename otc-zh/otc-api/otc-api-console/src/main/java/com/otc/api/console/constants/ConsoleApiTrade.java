package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by fenggq on 17-4-26.
 */
public interface ConsoleApiTrade extends ApiBasePathConstant {

    /**
     * 前缀：用户前缀
     */
    String PREFIX_TRADE_CONSOLE_API = CONSOLE_API+"/trade";

    /**
     * 查询交易列表
     */
    String TRADE_LIST_QUERY_API = PREFIX_TRADE_CONSOLE_API+"/list"+PATH_QUERY_JSON;


    /**
     * 确认放放币 == 申诉
     */
    String TRADE_CONFIRM_API = PREFIX_TRADE_CONSOLE_API+"/confirm";


    /**
     * 取消交易 == 申诉
     */
    String TRADE_CANCEL_API = PREFIX_TRADE_CONSOLE_API+"/cancel";


    /**
     * 确认放放币 == 进行中
     */
    String TRADE_CONFIRM_RUN_API = PREFIX_TRADE_CONSOLE_API+"/confirmRun";


    /**
     * 获取交易评价
     */
    String TRADE_JUDGE_DETAIL_API = PREFIX_TRADE_CONSOLE_API+"/judgeDetail"+PATH_QUERY_JSON;




}
