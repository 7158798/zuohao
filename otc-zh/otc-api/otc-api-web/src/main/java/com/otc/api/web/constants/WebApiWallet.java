package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-19.
 */
public interface WebApiWallet extends ApiBasePathConstant {

    //
    String PREFIX_WALLET_WEB_API = WEB_API + "/wallet";
    // 所有钱包
    String LIST_WALLET_WEB_API = PREFIX_WALLET_WEB_API + PATH_LIST + PATH_QUERY_JSON;
    // 单个钱包
    String INFO_WALLET_WEB_API = PREFIX_WALLET_WEB_API + "/info" + PATH_QUERY_JSON;
}
