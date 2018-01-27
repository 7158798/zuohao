package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by lx on 17-4-21.
 */
public interface ConsoleApiCoin extends ApiBasePathConstant {

    /**
     * 前缀：币种
     */
    String PREFIX_COIN_CONSOLE_API = CONSOLE_API + "/coin";
    // 币种分页列表
    String LIST_COIN_CONSOLE_API = PREFIX_COIN_CONSOLE_API + PATH_LIST + PATH_QUERY_JSON;
    //  获取币列表
    String LIST_COIN_TYPE_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/type" + PATH_LIST + PATH_QUERY_JSON;
    // 增加币种
    String ADD_COIN_CONSOLE_API = PREFIX_COIN_CONSOLE_API + PATH_ADD;
    // 更新币种
    String UPDATE_COIN_CONSOLE_API = PREFIX_COIN_CONSOLE_API + PATH_UPDATE;
    // 切换状态
    String CUT_STATUS_COIN_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/cut/status";
    // 获取币种详情
    String DETAIL_COIN_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/detail" + PATH_QUERY_JSON;
    // 新增地址
    String CREATE_ADDRESS_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/create/address";
    // 测试钱包
    String TEST_WALLET_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/test/wallet" + PATH_QUERY_JSON;
    // 增加提现手续费配置
    String ADD_COINFEE_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/addCoinFee";
    // 提现手续费配置
    String DETAIL_COINFEE_CONSOLE_API = PREFIX_COIN_CONSOLE_API + "/detailCoinFee" + PATH_QUERY_JSON;
}
