package com.otc.core.cache;

import com.jucaifu.common.log.LOG;
import org.apache.poi.ss.formula.functions.T;

/**
 * 注：此holiday_cache_key仅存储最新的一条记录
 * Created by luwei on 17-5-16.
 */
public class HolidayCacheManager {

    public static final String HOLIDAY_KEY = "holiday_cache_key";


    public static boolean saveCache(GeneralListCache<T> obj) {
        boolean flag = false;
        if(obj == null) {
            LOG.i(HolidayCacheManager.class, "缓存数据为空");
            return flag;
        }

        flag = CacheHelper.saveObj(HOLIDAY_KEY, obj);
        if(!flag) {
            LOG.i(HolidayCacheManager.class, "保存缓存数据，key = " + HOLIDAY_KEY + "，返回状态: " + flag);
        }
        return flag;
    }

    public static GeneralListCache getCacheObj() {
        return CacheHelper.getObj(HOLIDAY_KEY);
    }


    //清理缓存，因为此key仅存储最新一条记录，所以只删除即可
    public static boolean deleteCache() {
        boolean flag = false;
        flag = RedisSessionManager.deleteSession(HOLIDAY_KEY);
        if(!flag) {
            LOG.i(HolidayCacheManager.class, "清除缓存数据，key = " + HOLIDAY_KEY + "，返回状态: " + flag);
        }
        return flag;
    }


}
