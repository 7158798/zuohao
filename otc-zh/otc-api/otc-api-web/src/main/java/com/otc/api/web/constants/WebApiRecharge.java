package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-19.
 */
public interface WebApiRecharge extends ApiBasePathConstant {

    /**
     * 前缀：充值前缀
     */
    String PREFIX_RECHARGE_WEB_API = WEB_API + "/recharge";
    // 获取充值地址
    String ADDRESS_RECHARGE_WEB_API = PREFIX_RECHARGE_WEB_API + "/address" + PATH_QUERY_JSON;

}
