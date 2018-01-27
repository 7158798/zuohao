package com.otc.core.cache;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注：此otc_fquote_cache_key仅存储最新的一条记录
 * Created by luwei on 17-5-16.
 */
public class FquoteCacheManager {

    public static final String FQUOTE_KEY = "otc_fquote_cache_key";


    public static boolean saveCache(String obj) {
        boolean flag = false;
        if(obj == null) {
            LOG.i(FquoteCacheManager.class, "缓存数据为空");
            return flag;
        }

        flag = CacheHelper.saveObj(FQUOTE_KEY, obj);
        if(!flag) {
            LOG.i(FquoteCacheManager.class, "保存缓存数据，key = " + FQUOTE_KEY + "，返回状态: " + flag);
        }
        return flag;
    }

    public static Map<String, Map> getCacheObj() {
        Map<String, Map> map = new HashMap<>();
        String cache = CacheHelper.getObj(FQUOTE_KEY);
        if(StringUtils.isBlank(cache)){
            return map;
        }
        map = JsonHelper.jsonStr2MapObj(cache, Map.class);
        return map;
    }

    public static Map<String, String> getCacheObj(Long key) {
        Map<String, Map> cacheObj = getCacheObj();
        Map<String, String> map = new HashMap<>();
        if(cacheObj != null && !cacheObj.isEmpty()){
            map = cacheObj.get(key.toString());
        }
        return map;
    }


    //清理缓存，因为此key仅存储最新一条记录，所以只删除即可
    public static boolean deleteCache() {
        boolean flag = false;
        flag = RedisSessionManager.deleteSession(FQUOTE_KEY);
        if(!flag) {
            LOG.i(FquoteCacheManager.class, "清除缓存数据，key = " + FQUOTE_KEY + "，返回状态: " + flag);
        }
        return flag;
    }


}
