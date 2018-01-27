package com.facade.core.wallet.cache;

import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;

import java.io.Serializable;

/**
 * Created by yangyy on 16-5-6.
 */
public class AppCacheManager {

    /**
     * 聚财富理财达人榜缓存key
     */
    private static final String JCF_APP_FINANCIAL_EXPERT_KEY = "jcf-app-financial-expert-key";

    /**
     *  聚财富理财达人榜缓存时间（单位：秒）
     */
    private static final Integer JCF_APP_FINANCIAL_EXPERT_TIME = TimeConstant.ONE_DAY_UNIT_SECONDS;


    /**
     * 存储app端聚财富理财达人榜
     *
     * @param <T>
     * @return
     */
    public static <T extends Serializable> boolean saveAppFinExpertCache(T obj) {
        boolean result = false;
        String key = JCF_APP_FINANCIAL_EXPERT_KEY;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_APP_FINANCIAL_EXPERT_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }

    /**
     * 获取redis缓存中存储的web端聚财富理财达人榜
     *
     * @param <T>
     * @return
     */
    public static <T extends Serializable > T getAppFinExpertCache() {
        T obj = CacheHelper.getObj(JCF_APP_FINANCIAL_EXPERT_KEY);
        return obj;
    }

}