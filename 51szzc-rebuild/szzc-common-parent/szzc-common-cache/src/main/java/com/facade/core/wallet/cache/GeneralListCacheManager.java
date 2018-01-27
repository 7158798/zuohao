package com.facade.core.wallet.cache;

import com.jucaifu.common.log.LOG;
import org.apache.poi.ss.formula.functions.T;

/**
 * Created by zygong on 17-6-12.
 */
public class GeneralListCacheManager {

    public static boolean saveCache(String key, GeneralListCache<T> obj, Integer seconds) {
        boolean flag = false;
        if(obj == null) {
            LOG.i(GeneralListCacheManager.class, "缓存数据为空");
            return flag;
        }

        flag = CacheHelper.saveObj(key, obj, seconds);
        if(!flag) {
            LOG.i(GeneralListCacheManager.class, "保存缓存数据，key = " + key + "，返回状态: " + flag);
        }
        return flag;
    }

    public static GeneralListCache getCacheObj(String key) {
        return CacheHelper.getObj(key);
    }


    //清理缓存，因为此key仅存储最新一条记录，所以只删除即可
    public static boolean deleteCache(String key) {
        boolean flag = false;
        flag = RedisSessionManager.deleteSession(key);
        if(!flag) {
            LOG.i(GeneralListCacheManager.class, "清除缓存数据，key = " + key + "，返回状态: " + flag);
        }
        return flag;
    }

}
