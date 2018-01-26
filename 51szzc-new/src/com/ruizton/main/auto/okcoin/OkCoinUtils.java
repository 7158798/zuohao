package com.ruizton.main.auto.okcoin;

import com.ruizton.main.log.LOG;
import com.ruizton.util.HttpClientHelper;
import com.ruizton.util.JsonHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * OkCoin 工具类
 * Created by lx on 17-2-10.
 */
public class OkCoinUtils implements OkCoinConstant {

    private static Map getParam(String api_key){
        Map<String,String> params = new HashMap();
        params.put("api_key",api_key);
        return params;
    }

    public static DepthResponse depth(String sysbol,Integer size){
        return get(DEPTH_URL + "?symbol=" + sysbol + "&size=" + size,DepthResponse.class);
    }

    /**
     * 行情
     * @param symbol btc_usd:比特币    ltc_usd :莱特币
     * @return
     */
    public static TickerResponse ticker(String symbol){
        return get(TICKER_URL + "?symbol=" + symbol,TickerResponse.class);
    }
    /**
     * 获取用户信息
     * @param api_key
     * @param secret_key
     * @return
     */
    public static UserResponse getUserInfo(String api_key,String secret_key){
        Map<String,String> params = getParam(api_key);
        return post(USERINFO_URL,params,secret_key,UserResponse.class);
        //return null;
    }

    /**
     * okcoin 下单
     * @param api_key
     * @param secret_key
     * @param symbol btc_usd: 比特币 ltc_usd: 莱特币
     * @param type 买卖类型： 限价单（buy/sell） 市价单（buy_market/sell_market）
     * @param price 下单价格 [限价买单(必填)： 大于等于0，小于等于1000000 |
     *                       市价买单(必填)： BTC :最少买入0.01个BTC 的金额(金额>0.01*卖一价) / LTC :最少买入0.1个LTC 的金额(金额>0.1*卖一价)]
     *
     * @param amount 交易数量 [限价卖单（必填）：BTC 数量大于等于0.01 / LTC 数量大于等于0.1 |
     *                        市价卖单（必填）： BTC :最少卖出数量大于等于0.01 / LTC :最少卖出数量大于等于0.1]
     * @return
     */
    public static OrderResponse trade(String api_key,String secret_key,String symbol, String type, String price, String amount){
        Map<String,String> params = getParam(api_key);
        if(!StringUtils.isEmpty(symbol)){
            params.put("symbol", symbol);
        }
        if(!StringUtils.isEmpty(type)){
            params.put("type", type);
        }
        if(!StringUtils.isEmpty(price)){
            params.put("price", price);
        }
        if(!StringUtils.isEmpty(amount)){
            params.put("amount", amount);
        }
        return post(TRADE_URL,params,secret_key,OrderResponse.class);
    }

    /**
     * 撤销订单
     * @param api_key
     * @param secret_key
     * @param symbol  btc_usd: 比特币 ltc_usd: 莱特币
     * @param order_id 订单ID(多个订单ID中间以","分隔,一次最多允许撤消3个订单)
     * @return
     */
    public static OrderResponse cancelOrder(String api_key,String secret_key,String symbol, String order_id){
        Map<String,String> params = getParam(api_key);
        if(!StringUtils.isEmpty(symbol)){
            params.put("symbol", symbol);
        }
        if(!StringUtils.isEmpty(order_id)){
            params.put("order_id", order_id);
        }
        return post(CANCEL_ORDER_URL,params,secret_key,OrderResponse.class);
    }

    /**
     * 获取用户的订单信息
     * @param api_key
     * @param secret_key
     * @param symbol  btc_usd: 比特币 ltc_usd: 莱特币
     * @param order_id  订单ID(-1查询全部订单，否则查询相应单号的订单)
     * @return
     */
    public static OrderInfoResponse orderInfo(String api_key,String secret_key,String symbol, String order_id){
        Map<String,String> params = getParam(api_key);
        if(!StringUtils.isEmpty(symbol)){
            params.put("symbol", symbol);
        }
        if(!StringUtils.isEmpty(order_id)){
            params.put("order_id", order_id);
        }

        return post(ORDERS_INFO_URL,params,secret_key,OrderInfoResponse.class);
    }


    /**
     * post 请求
     * @param url
     * @param params
     * @param secret_key
     * @return
     */
    private static <T> T post(String url,Map<String,String> params,String secret_key,Class<T> clz){
        try {
            // 签名
            String sign = MD5Util.buildMysignV1(params,secret_key);
            params.put("sign",sign);
            String result = HttpClientHelper.post(url, params);
            LOG.i(OkCoinUtils.class,"接口请求结果 " + result);
            return JsonHelper.jsonStr2Obj(result,clz);
        } catch (Exception e) {
            LOG.e(OkCoinUtils.class,e.getMessage(),e);
        }
        return null;
    }

    /**
     * get 请求
     * @param url
     * @return
     */
    private static <T> T  get(String url,Class<T> clz){
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        LOG.i(OkCoinUtils.class,"接口请求结果 " + result);
        T t = JsonHelper.jsonStr2Obj(result,clz);
        return t;
    }

    public static void main(String[] args) {
        String api_key = "f37cc91d-f1b5-4716-8363-054f7b0fcd01";
        String secret_key = "F15DB8B14A610AF7B14BC252C84C936D";
        //UserResponse response = getUserInfo(api_key,secret_key);
        trade(api_key,secret_key,"ltc_cny","buy","20.58","0.09");
        // OrderInfoResponse response = orderInfo(api_key,secret_key,"ltc_cny","400830293");
        //cancelOrder(api_key,secret_key,"btc_cny","8450310480");
        //TickerResponse response = ticker("ltc_cny");
        //DepthResponse depth = depth("btc_cny",1);
        System.out.println("sddfadsf");
    }


}
