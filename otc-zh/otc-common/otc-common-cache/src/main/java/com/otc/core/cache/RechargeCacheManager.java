package com.otc.core.cache;

import com.otc.core.pojo.RechargePo;


/**
 * Created by lx on 17-4-20.
 */
public class RechargeCacheManager {

    /**
     * Save recharge obj boolean.
     *
     * @param key the key
     * @param obj the obj
     * @return the boolean
     */
    public static boolean saveRechargeObj(String key, RechargePo obj) {

        return CacheHelper.saveObj(key, obj);
    }

    /**
     * Gets recharge obj.
     *
     * @param key the key
     * @return the recharge obj
     */
    public static RechargePo getRechargeObj(String key) {
        return CacheHelper.getObj(key);
    }

    /**
     * Delete recharge obj boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean deleteRechargeObj(String key) {
        return CacheHelper.deleteObj(key);
    }
}
