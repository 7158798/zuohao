package com.facade.core.wallet.cache;

import com.jucaifu.common.log.LOG;
import com.szzc.facade.fentrustLog.pojo.dto.TradeListDto;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zygong on 17-5-22.
 */
public class OtherPlatformTradeCacheManager {
    public static final String TRADEMESSAGE_KEY = "TRADE_OTHERPLATFORM_CACHE_KEY";

    /**
     * Save TradeMessage obj.
     *
     */
    public static boolean saveTradeMessageObj(Map<String, TradeListDto> map, Integer seconds) {
        boolean result = false;
        try{
            OtherPlatformTradeCache cache = getCache();
            if(cache == null){
                cache = OtherPlatformTradeCache.newInstance();
            }
            cache.setMap(map);
            result = CacheHelper.saveObj(TRADEMESSAGE_KEY, cache);
        }catch (Exception e) {
            LOG.e(OtherPlatformTradeCacheManager.class, "保存缓存失败 ：", e);
        }
        return result;
    }

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

    public static Map<String, TradeListDto> getAllCache(){
        Map<String, TradeListDto> map = new HashMap<>();
        OtherPlatformTradeCache OtherPlatformCache = getCache();
        if (OtherPlatformCache != null){
            map = OtherPlatformCache.getMap();
        }
        return map;
    }

    public static TradeListDto getByKey(String key){
        TradeListDto vo = null;
        Map<String, TradeListDto> map = null;

        if(StringUtils.isBlank(key)){
            return vo;
        }
        OtherPlatformTradeCache otherPlatformCache = getCache();
        if (otherPlatformCache != null){
            map = otherPlatformCache.getMap();
            if(map != null && !map.isEmpty()){
                vo = map.get(key);
            }
        }
        return vo;
    }

    /**
     * Gets cache.
     *
     * @return the cache
     */
    private static OtherPlatformTradeCache getCache() {

        OtherPlatformTradeCache prohibitCache = null;

        Object obj = RedisSessionManager.getSessionObj(TRADEMESSAGE_KEY);
        if (obj instanceof OtherPlatformTradeCache) {
            prohibitCache = (OtherPlatformTradeCache) obj;
        }

        return prohibitCache;
    }

    private static void i(String msg) {
        LOG.i(OtherPlatformTradeCacheManager.class, msg);
    }

    //清理缓存，因为此key仅存储最新一条记录，所以只删除即可
    public static boolean deleteCache() {
        boolean flag = false;
        flag = RedisSessionManager.deleteSession(TRADEMESSAGE_KEY);
        return flag;
    }
}
