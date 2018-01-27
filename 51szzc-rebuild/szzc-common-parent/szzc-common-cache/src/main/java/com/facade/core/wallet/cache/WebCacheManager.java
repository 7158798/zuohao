package com.facade.core.wallet.cache;

import com.facade.core.wallet.pojo.WebCacheObj;
import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;

import java.io.Serializable;

/**
 * Created by yangyy on 16-1-29.
 */
public class WebCacheManager {

    /**
     * 聚财富理财达人榜缓存key
     */
    private static final String JCF_FINANCIAL_EXPERT_KEY = "jcf-financial-expert-key";

    /**
     * 聚财富理财成绩缓存key
     */
    private static final String JCF_FINANCIAL_GRADE_KEY = "jcf-financial-grade-key";
    /**
     * 聚财富理财成绩appkey
     */
    private static final String JCF_FINANCIAL_APP_GRADE_KEY = "jcf-financial-app-grade-key";

    /**
     * 理财服务 银行列表缓存key
     */
    private static final String JCF_INFO_BANK_LIST = "jcf-info-bank-list-key";

    /**
     * 理财服务 外汇列表缓存key
     */
    public static final String JCF_INFO_EXCHANGE_LIST = "jcf-info-exchange-exchange-key";

    /**
     * 理财服务 银行利率
     */
    public static final String JCF_INFO_RATE_DETAIL = "jcf-info-rate";

    /**
     *  聚财富理财达人榜缓存时间（单位：秒）
     */
    public static final Integer JCF_FINANCIAL_EXPERT_TIME = TimeConstant.ONE_DAY_UNIT_SECONDS;

    /**
     *  聚财富理财成绩缓存时间（单位：秒）
     */
    public static final Integer JCF_FINANCIAL_GRADE_TIME = TimeConstant.ONE_DAY_UNIT_SECONDS;


    /**
     *  理财服务 银行列表（单位：秒）
     */
    public static final Integer JCF_INFO_BANK_LIST_TIME = 10* TimeConstant.ONE_MINUTE_UNIT_SECONDS;

    /**
     *  理财服务 外汇利率（单位：秒）
     */
    public static final Integer JCF_INFO_EXCHANGE_LIST_TIME = 10* TimeConstant.ONE_MINUTE_UNIT_SECONDS;

    /**
     *  理财服务 银行利率（单位：秒）
     */
    public static final Integer JCF_INFO_RATE_TIME = 10* TimeConstant.ONE_MINUTE_UNIT_SECONDS;

    /**
     * 存储web端聚财富理财达人榜
     *
     * @param <T>
     * @return
     */
    public static <T extends Serializable> boolean saveWebFinExpertCache(WebCacheObj<T> obj) {
        boolean result = false;
        String key = JCF_FINANCIAL_EXPERT_KEY;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_FINANCIAL_EXPERT_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }

    /**
     * 获取redis缓存中存储的web端聚财富理财达人榜
     *
     * @param <T>
     * @return
     */
    public static <T> WebCacheObj<T> getWebFinExpertCache() {
        WebCacheObj<T> obj = CacheHelper.getObj(JCF_FINANCIAL_EXPERT_KEY);
        return obj;
    }

    /**
     * 存储web端聚财富理财成绩数据
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends Serializable> Boolean saveWebFinGradeObj(WebCacheObj<T> obj) {
        boolean result = false;
        String key = JCF_FINANCIAL_GRADE_KEY;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_FINANCIAL_GRADE_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }


    /**
     * 存储app端聚财富理财成绩数据
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T extends Serializable> Boolean saveAppFinGradeObj(WebCacheObj<T> obj) {
        boolean result = false;
        String key = JCF_FINANCIAL_APP_GRADE_KEY;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_FINANCIAL_GRADE_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }

    /**
     * 存储银行列表数据
     */
    public static <T extends Serializable> Boolean saveWebInfoBankList(WebCacheObj<T> obj) {
        boolean result = false;
        String key = JCF_INFO_BANK_LIST;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_INFO_BANK_LIST_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }

    /**
     * 存储银行列表数据
     */
    public static <T extends Serializable> Boolean saveWebInfoExchangeList(WebCacheObj<T> obj) {
        boolean result = false;
        String key = JCF_INFO_EXCHANGE_LIST;
        if (obj == null) {
            LOG.i("", "缓存数据为空");
            return result;
        }
        Integer time = JCF_INFO_EXCHANGE_LIST_TIME;

        result = CacheHelper.saveObj(key, obj, time);

        return result;
    }


    /**
     * 获取redis缓存中存储的数据
     *
     * @param <T>
     * @return
     */
    public static <T> WebCacheObj<T> getAppFinGradeObj() {
        WebCacheObj<T> obj = CacheHelper.getObj(JCF_FINANCIAL_APP_GRADE_KEY);
        return obj;
    }
    /**
     * 获取redis缓存中存储的数据
     *
     * @param <T>
     * @return
     */
    public static <T> WebCacheObj<T> getWebFinGradeObj() {
        WebCacheObj<T> obj = CacheHelper.getObj(JCF_FINANCIAL_GRADE_KEY);
        return obj;
    }

    /**
     * 获取理财服务银行列表
     *
     * @param <T>
     * @return
     */
    public static <T> WebCacheObj<T> getWebInfoBankList() {
        WebCacheObj<T> obj = CacheHelper.getObj(JCF_INFO_BANK_LIST);
        return obj;
    }

    /**
     * 获取理财服务外汇利率列表
     *
     * @param <T>
     * @return
     */
    public static <T> WebCacheObj<T> getWebInfoExchangeList() {
        WebCacheObj<T> obj = CacheHelper.getObj(JCF_INFO_EXCHANGE_LIST);
        return obj;
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
        try {
            result = RedisSessionManager.saveSession(key, value, seconds);
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




}