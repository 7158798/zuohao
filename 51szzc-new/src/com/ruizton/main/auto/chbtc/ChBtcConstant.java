package com.ruizton.main.auto.chbtc;

/**
 * Created by lx on 17-2-10.
 */
public interface ChBtcConstant {

    String BASE_URL = "https://trade.chbtc.com/api/";
    // 下单
    String ORDER_URL = BASE_URL + "order";
    // 取消订单
    String CANCEL_ORDER_URL = BASE_URL + "cancelOrder";
    // 订单信息
    String ORDER_INFO_URL = BASE_URL + "getOrder";
    // 获取用户信息
    String USERINFO_URL = BASE_URL + "getAccountInfo";
}
