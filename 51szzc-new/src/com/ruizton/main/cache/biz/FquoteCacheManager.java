package com.ruizton.main.cache.biz;

import com.ruizton.main.cache.CacheHelper;
import com.ruizton.main.cache.RedisSessionManager;
import com.ruizton.main.log.LOG;
import org.apache.poi.hssf.record.formula.functions.T;

/**
 * 注：此fquote_cache_key仅存储最新的一条记录
 * Created by luwei on 17-5-16.
 */
public class FquoteCacheManager {

    public static final String FQUOTE_KEY = "fquote_cache_key";


    public static boolean saveCache(GeneralListCache<T> obj) {
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

    public static GeneralListCache getCacheObj() {
        return CacheHelper.getObj(FQUOTE_KEY);
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
