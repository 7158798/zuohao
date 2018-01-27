package com.facade.core.wallet.cache;

import com.jucaifu.common.constants.TimeConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.DateHelper;

import java.util.Date;
import java.util.Set;

/**
 * Created by zh on 16-8-9.
 */
public class RedPacketCacheManager {
    //产品动态信息列表key
    public static final String REDPACKET_POOL = "REDPACKET_POOL";

    private static Integer DEFAULT_SESSION_TIMEOUT = 3 * TimeConstant.ONE_HOUR_UNIT_SECONDS;

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

    /**
     * 获取缓存key
     * @param uuid
     * @param startTime
     * @param endTime
     * @param now
     * @return
     */
    public static String getCacheKey(String uuid,Date startTime,Date endTime,Date now){
        String dateStr = DateHelper.date2String(now, DateHelper.DateFormatType.YearMonthDay);
        String startStr = DateHelper.date2String(startTime, DateHelper.DateFormatType.HourMinuteSecond);
        String endStr = DateHelper.date2String(endTime, DateHelper.DateFormatType.HourMinuteSecond);
        String key = uuid+":"+dateStr+":"+startStr+"-"+endStr;
        return RedPacketCacheManager.REDPACKET_POOL+":"+key;
    }
}

