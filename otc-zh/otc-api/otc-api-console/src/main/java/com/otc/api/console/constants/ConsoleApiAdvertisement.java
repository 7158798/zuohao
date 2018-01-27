package com.otc.api.console.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by zygong on 17-4-27.
 */
public interface ConsoleApiAdvertisement extends ApiBasePathConstant {

    // 广告
    String PREFIX_ADVERTISEMENT_CONSOLE_API = "/advertisement";
    // 获取广告列表
    String  GETLIST_ADVERTISEMENT_CONSOLE_API = PREFIX_ADVERTISEMENT_CONSOLE_API + "/getAdvertisementList";
    // 关闭
    String  UPDATESTATUS_ADVERTISEMENT_CONSOLE_API = PREFIX_ADVERTISEMENT_CONSOLE_API + "/updateStatus";

    // 交易说明
    String PREFIX_TRANSACTION_CONSOLE_API = "/transactionDes";
    // 列表
    String  GETLIST_TRANSACTIONDES_CONSOLE_API = PREFIX_TRANSACTION_CONSOLE_API + "/getTransactionList";
    // 保存
    String SAVE_TRANSACTIONDES_CONSOLE_API = PREFIX_TRANSACTION_CONSOLE_API + "/save";
    // 更新
    String UPDATE_TRANSACTIONDES_CONSOLE_API = PREFIX_TRANSACTION_CONSOLE_API + "/update";
    // 详情
    String DETAIL_TRANSACTIONDES_CONSOLE_API = PREFIX_TRANSACTION_CONSOLE_API + "/detail" + PATH_QUERY_JSON;
    // 删除
    String DELETE_TRANSACTIONDES_CONSOLE_API = PREFIX_TRANSACTION_CONSOLE_API + "/delete"  + PATH_QUERY_JSON;

    // 价格公式
    String PREFIX_PRICEFORMULA_CONSOLE_API = "/priceFormula";
    // 获取列表
    String  GETLIST_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/getPriceFormulaList";
    //保存
    String SAVE_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/save";
    // 删除
    String DELETE_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/delete" + PATH_QUERY_JSON;
    // 详情
    String DETAIL_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/detail" + PATH_QUERY_JSON;
    // 更新
    String UPDATE_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/update";
    // 虚拟币列表
    String COINLIST_PRICEFORMULA_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/coinList";
    // 获取其他平台数据
    String OTHERPLATFORMPRICE_ADVERTISEMENT_CONSOLE_API = PREFIX_PRICEFORMULA_CONSOLE_API + "/otherPlatformPrice" + PATH_QUERY_JSON;

}
