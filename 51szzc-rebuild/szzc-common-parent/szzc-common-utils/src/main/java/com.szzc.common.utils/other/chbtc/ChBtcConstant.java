package com.szzc.common.utils.other.chbtc;

/**
 * Created by lx on 17-2-10.
 */
public interface ChBtcConstant {

    String BASE_URL = "http://api.chbtc.com/data/v1/ticker/";
    // 下单
    String ORDER_URL = BASE_URL + "order";
    // 取消订单
    String CANCEL_ORDER_URL = BASE_URL + "cancelOrder";
    // 订单信息
    String ORDER_INFO_URL = BASE_URL + "getOrder";
    // 获取用户信息
    String USERINFO_URL = BASE_URL + "getAccountInfo";
    // 获取市场行情
    String TICKER_URL = BASE_URL + "ticker";
}
