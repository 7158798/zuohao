package com.jucaifu.common.util.cache;

/**
 * GlobalCacheHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/17.
 * <p>
 * =====================================================
 * 当前仅仅是模拟缓存的基本思路，后面和陆续集成 Memcached ,Redis
 * =====================================================
 */
public class GlobalCacheHelper extends UnitCacheHelper {


    private static GlobalCacheHelper sInstance = null;

    private GlobalCacheHelper() {
        super();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GlobalCacheHelper getInstance() {

        if (sInstance == null) {
            synchronized (GlobalCacheHelper.class) {
                if (sInstance == null) {
                    sInstance = new GlobalCacheHelper();
                }
            }
        }

        return sInstance;
    }
}
