package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-21.
 */
public interface ConsoleApiPool extends ApiBasePathConstant {

    /**
     * 前缀：充值前缀
     */
    String PREFIX_POOL_CONSOLE_API = CONSOLE_API + "/pool";
    // 可用地址统计
    String COUNT_USED_POOL_CONSOLE_API = PREFIX_POOL_CONSOLE_API + "/used/count" + PATH_QUERY_JSON;

}
