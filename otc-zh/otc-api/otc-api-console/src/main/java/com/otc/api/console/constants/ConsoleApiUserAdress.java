package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by fenggq on 17-5-2.
 */
public interface ConsoleApiUserAdress extends ApiBasePathConstant {
    /**
     * 前缀：用户kyc前缀
     */
    String PREFIX_USERADRESS_CONSOLE_API = CONSOLE_API+ "/userAdress";

    /**
     * 查询用户充值地址列表
     */
    String LIST_QUERY_API = PREFIX_USERADRESS_CONSOLE_API+"/list"+PATH_QUERY_JSON;
}
