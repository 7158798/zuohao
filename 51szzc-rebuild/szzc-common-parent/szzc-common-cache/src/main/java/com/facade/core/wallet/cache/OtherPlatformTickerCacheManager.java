package com.facade.core.wallet.cache;

import com.jucaifu.common.util.JsonHelper;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import com.szzc.facade.out.pojo.TickerDateDto;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static org.openxmlformats.schemas.drawingml.x2006.chart.STTrendlineType.LOG;

/**
 * Created by zygong on 17-5-22.
 */
public class OtherPlatformTickerCacheManager {
    public static final String TRADEMESSAGE_KEY = "TICKER_OTHERPLATFORM_CACHE_KEY";

    /**
     * Gets TradeMessage obj.
     *
     * @param key the key
     * @return the sms obj
     */
    public static <T> T getTradeMessageObj(String key) {
        return CacheHelper.getObj(key);
    }

    /**
     * Delete TradeMessage obj.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean deleteTradeMessageObj(String key) {
        return CacheHelper.deleteObj(key);
    }


    public static TickerDateDto getByKey(String key){
        TickerDateDto vo = null;
        Map<String, TickerDateDto> map = null;

        if(StringUtils.isBlank(key)){
            return vo;
        }
        map = getCache();
        if(map != null || !map.isEmpty()){
            vo = map.get(key);
        }
        return vo;
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    private static Map getCache() {

        OtherPlatformTickerCache prohibitCache = null;

        String obj = RedisSessionManager.getSessionObj(TRADEMESSAGE_KEY);
        if(StringUtils.isBlank(obj)){
            return null;
        }
        Map<String, TickerDateDto> map = JsonHelper.jsonStr2MapObj(obj, TickerDateDto.class);

        return map;
    }

    //清理缓存，因为此key仅存储最新一条记录，所以只删除即可
    public static boolean deleteCache() {
        boolean flag = false;
        flag = RedisSessionManager.deleteSession(TRADEMESSAGE_KEY);
        return flag;
    }
}
