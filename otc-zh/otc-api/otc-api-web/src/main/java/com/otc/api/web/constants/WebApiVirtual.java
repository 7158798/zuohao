package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-19.
 */
public interface WebApiVirtual extends ApiBasePathConstant {

    /**
     * 前缀：充值前缀
     */
    String PREFIX_VIRTUAL_WEB_API = WEB_API + "/virtual";
    // 虚拟币记录
    String lIST_RECORD_VIRTUAL_WEB_API = PREFIX_VIRTUAL_WEB_API + "/record" + PATH_LIST + PATH_QUERY_JSON;

    // 虚拟币列表
    String LIST_COIN_VIRTUAL_WEB_API = PREFIX_VIRTUAL_WEB_API + "/coin" + PATH_LIST;
}
