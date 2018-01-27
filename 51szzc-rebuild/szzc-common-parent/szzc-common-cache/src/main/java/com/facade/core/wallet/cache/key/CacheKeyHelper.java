package com.facade.core.wallet.cache.key;

/**
 * Created by lx on 17-6-29.
 */
public class CacheKeyHelper {

    public final static String SINGLE_ORDER_DATA_KEY = "single_order_data_key_";

    /**
     * 单笔自动挂单
     * @param shortName
     * @return
     */
    public static String buildSingleOrderKey(String shortName){
        return SINGLE_ORDER_DATA_KEY + "_" + shortName;
    }
}
