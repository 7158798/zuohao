package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by a123 on 17-4-26.
 */
public interface ConsoleApiUserKyc extends ApiBasePathConstant {

    /**
     * 前缀：用户kyc前缀
     */
    String PREFIX_USERKYC_CONSOLE_API =   CONSOLE_API+"/userKyc";

    /**
     * 查询用户kyc列表
     */
    String USERKYC_LIST_QUERY_API = PREFIX_USERKYC_CONSOLE_API+"/list"+PATH_QUERY_JSON;

    /**
     * 通过审核
     */
    String USERKYC_PASS_API = PREFIX_USERKYC_CONSOLE_API+"/pass";

    /**
     * 拒绝审核
     */
    String USERKYC_NOPASS_API = PREFIX_USERKYC_CONSOLE_API+"/nopass";
    
}
