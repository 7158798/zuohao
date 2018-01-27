package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-21.
 */
public interface ConsoleApiWallet extends ApiBasePathConstant {

    /**
     * 前缀：充值前缀
     */
    String PREFIX_WALLET_CONSOLE_API = CONSOLE_API + "/wallet";
    // 可用地址统计
    String LIST_WALLET_CONSOLE_API = PREFIX_WALLET_CONSOLE_API + PATH_LIST + PATH_QUERY_JSON;

}
