package com.facade.core.wallet.cache;

import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;

import java.util.Set;

/**
 * Created by yong on 16-1-25.
 */
public class ProductCacheManager {
    //产品动态信息列表key
    public static String PRO_DYNAMIC_INFO = "PRO_DYNAMIC_INFO";
    //产品的历史兑付金额key
    public static String PRO_COUNT_TOTAL = "PRO_COUNT_TOTAL";
    //产品对应的成功订单列表key
    public static String PRO_ORDER_LIST = "PRO_ORDER_LIST";
    //产品销售统计信息Key
    public static String PRO_SALE_COUNT_INFO = "PRO_SALE_COUNT_INFO";
    //产品剩余额度
    public static String PRO_REST_AMOUNT = "PRO_REST_AMOUNT";

    public static String PRO_BANNER_INFO = "PRO_BANNER_INFO";

    public static String PRO_LIST = "PRO_LIST";

    public static String PRO_RECOMMANE_LIST = "PRO_RECOMMANE_LIST";

    private static Integer DEFAULT_SESSION_TIMEOUT = 3 * TimeConstant.ONE_HOUR_UNIT_SECONDS;

    /**
     * 产品动态信息key
     *
     * @param str
     * @return
     */
    public static String buildKey(String str, String prefix) {
        return prefix + ":" + str;
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static boolean saveInfo(String key, String value) {
        return RedisSessionManager.saveSession(key, value);
    }

    /**
     * 保存缓存带上时间，单位妙
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static boolean saveInfoWithTime(String key, String value, Integer seconds) {

        boolean result = false;
        Integer cachedSecondes = seconds;

        if (cachedSecondes == null) {
            cachedSecondes = DEFAULT_SESSION_TIMEOUT;
        }
        try {
            result = RedisSessionManager.saveSession(key, value, cachedSecondes);
        } catch (Exception e) {
            LOG.e(RedisSessionManager.class, "保存缓存失败 ：", e);
        }
        return result;
    }

    /**
     * @param key
     * @return
     */
    public static String getInfo(String key) {
        return RedisSessionManager.getSessionObj(key);
    }

    /**
     * 删除cache
     *
     * @param key
     * @return
     */
    public static boolean deleteCache(String key) {
        return RedisSessionManager.deleteSession(key);
    }

    public static Set<byte[]> keysPreStr(String key) {
        return RedisManager.keys(key);
    }
}
