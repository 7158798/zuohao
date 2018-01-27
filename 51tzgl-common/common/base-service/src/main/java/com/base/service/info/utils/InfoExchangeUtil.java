package com.base.service.info.utils;

import com.alibaba.fastjson.JSONObject;
import com.jucaifu.common.configs.BusinessConfig;
import com.jucaifu.common.context.SpringPropReaderHelper;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.util.JsonHelper;
import com.base.facade.info.enums.ExchangeConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxun on 16-8-23.
 */
public class InfoExchangeUtil implements ExchangeConstant {

    public static Map params = null;
    private static String exchangeUrl = null;

    /**
     * 获取请求参数
     * @return
     */
    public static Map<String,String> getParams(){

        if (params == null){
            synchronized (InfoExchangeUtil.class){
                if (params == null) {
                    params = new HashMap<>();
                    /*params.put(APP_KEY, BusinessConfig.INFO_EXCHANGE_KEY);
                    params.put(SIGN_KEY,BusinessConfig.INFO_EXCHANGE_SIGN);
                    exchangeUrl = BusinessConfig.INFO_EXCHANGE_URL;*/
                    exchangeUrl = SpringPropReaderHelper.getProperty("info.exchange.url");
                    params.put(APP_KEY, SpringPropReaderHelper.getProperty("info.exchange.key"));
                    params.put(SIGN_KEY,SpringPropReaderHelper.getProperty("info.exchange.sign"));
                }
            }
        }

        return params;
    }

    /**
     * 获取人民币外汇牌价
     * @param curNo     根据币种
     * @return
     */
    public static String getExchangeDetail(String curNo){
        String result =null;
        Map<String,String> params = getParams();
        // 设置获取的币种
        params.put(CURNO_KEY, curNo);

        try {
            // result = net(exchangeUrl, params, "GET");
            result = HttpClientHelper.post(exchangeUrl,params);
            LOG.i(InfoExchangeUtil.class,"请求接口的结果：" + result);
            result = analysis(result,curNo);
        } catch (Exception e) {
            // 接口获取数据异常
            LOG.e(InfoExchangeUtil.class,e.getMessage(),e);
        }

        return result;
    }

    /**
     * 解析结果
     * @param json
     * @param curNo
     * @return
     */
    public static String analysis(String json,String curNo){

        String data = null;
        JSONObject jsonObject = JsonHelper.jsonStr2JsonObj(json);
        String rms = (String) jsonObject.get(RESULT_KEY);
        if(!StringUtils.equals(rms,SUCCESSS)){
            // 调用接口返回失败,终止本次处理
            LOG.i(InfoExchangeUtil.class, jsonObject.get("msg"));
            return data;
        }
        jsonObject = (JSONObject) jsonObject.get(RESULT_DATA_KEY);

        return jsonObject.get(curNo).toString();
    }

}
