package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-19.
 */
public interface WebApiWithdraw extends ApiBasePathConstant {

    /**
     * 前缀：充值前缀
     */
    String PREFIX_WITHDRAW_WEB_API = WEB_API + "/withdraw";
    // 提现初始页面
    String INIT_WITHDRAW_WEB_API = PREFIX_WITHDRAW_WEB_API + "/init" + PATH_QUERY_JSON;
    // 新增提现
    String ADD_WITHDRAW_WEB_API = PREFIX_WITHDRAW_WEB_API + "/add";
    // 获取提现手续费
    String FEES_WITHDRAW_WEB_API = PREFIX_WITHDRAW_WEB_API + "/fees";
    // 取消提现
    String CANCEL_WITHDRAW_WEB_API = PREFIX_WITHDRAW_WEB_API + "/cancel";
}
