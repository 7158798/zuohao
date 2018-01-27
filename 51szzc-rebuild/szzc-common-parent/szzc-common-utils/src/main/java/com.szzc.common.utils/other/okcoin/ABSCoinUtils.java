package com.szzc.common.utils.other.okcoin;


import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;

import java.util.Map;

/**
 * Created by lx on 17-6-28.
 */
public abstract class ABSCoinUtils {

    private static String CNY = "cny";

    /**
     * get 请求
     *
     * @param <T> the type parameter
     * @param url the url
     * @param clz the clz
     * @return t t
     */
    protected static <T> T get(String url,Class<T> clz){
        String result = HttpClientHelper.sendGetRequest(url, Boolean.FALSE);
        T t = JsonHelper.jsonStr2Obj(result, clz);
        return t;
    }

    /**
     * post 请求
     * @param url
     * @param params
     * @param secret_key
     * @return
     */
    protected static <T> T post(String url,Map<String,String> params,String secret_key,Class<T> clz){
        try {
            // 签名
            String sign = MD5Util.buildMysignV1(params,secret_key);
            params.put("sign",sign);
            String result = HttpClientHelper.post(url, params);
            LOG.i(OkCoinUtils.class,"接口请求结果 " + result);
            return JsonHelper.jsonStr2Obj(result,clz);
        } catch (Exception e) {
            LOG.e(ABSCoinUtils.class,e.getMessage(),e);
        }
        return null;
    }

    /**
     * Bulid string.
     *
     * @param name the name
     * @return the string
     */
    protected static String bulid(String name){
        return name +"_"+ CNY;
    }

}
