package com.otc.api.web.constants;

import com.jucaifu.common.constants.ApiBasePathConstant;

/**
 * Created by fenggq on 17-5-11.
 */
public interface WebApiTrade extends ApiBasePathConstant {
    /**
     * Web 用户模块接口前缀
     */
    String PREFIX_WEB_TRADE_API =  WEB_API+"/trade";


    //发起交易
    String WEB_LAUNCH_TRADE_API = PREFIX_WEB_TRADE_API+"/launch";

    //买家完成付款
    String WEB_BUYER_PAYED_API = PREFIX_WEB_TRADE_API+"/payed";

    //卖家确认  --- 完成交易
    String WEB_SELLER_CONFIRM_API = PREFIX_WEB_TRADE_API+"/confirm";

    //卖家申诉
    String WEB_SELLER_APPEAL_API = PREFIX_WEB_TRADE_API+"/appeal";

    //买家取消交易
    String WEB_BUYER_CANCEL_API = PREFIX_WEB_TRADE_API+"/cancel";

    String WEB_JUDGE_TRADE_API = PREFIX_WEB_TRADE_API+"/judge";

    //获取交易详情
    String WEB_TRADE_INFO_API = PREFIX_WEB_TRADE_API+"/info"+PATH_QUERY_JSON;


    //获取交易记录
    String WEB_TRADE_LIST_API = PREFIX_WEB_TRADE_API+"/list"+PATH_QUERY_JSON;

    //获取评价详情
    String WEB_JUDGE_DETAIL_API = PREFIX_WEB_TRADE_API+"/judge"+PATH_QUERY_JSON;

    //系统取消单条超时交易
    String WEB_SYSTEM_CANCEL_API = PREFIX_WEB_TRADE_API+"/sysCancell"+PATH_QUERY_JSON;

}
