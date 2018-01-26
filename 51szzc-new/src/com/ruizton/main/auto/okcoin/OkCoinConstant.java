package com.ruizton.main.auto.okcoin;

/**
 * Created by lx on 17-2-10.
 */
public interface OkCoinConstant {

    String BASE_URL = "https://www.okcoin.cn/api/v1/";

    String USERINFO_URL = BASE_URL + "userinfo.do";
    // 下单
    String TRADE_URL = BASE_URL + "trade.do";
    // 取消下单
    String CANCEL_ORDER_URL = BASE_URL + "cancel_order.do";
    // 订单信息
    String ORDERS_INFO_URL = BASE_URL +"order_info.do";
    // 行情接口
    String TICKER_URL = BASE_URL + "ticker.do";
    // 深度接口
    String DEPTH_URL = BASE_URL + "depth.do";
}
