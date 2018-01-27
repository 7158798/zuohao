package com.facade.core.wallet.cache;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.szzc.facade.fquotes.pojo.po.Fquotes;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 注：此fquote_cache_key仅存储最新的一条记录
 * Created by luwei on 17-5-16.
 */
public class FquoteCacheManager {

    public static final String FQUOTE_KEY = "fquote_cache_key";


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

    public static List<Fquotes> getCacheObj() {
        List<Fquotes> list = new ArrayList<>();
        String cache = CacheHelper.getObj(FQUOTE_KEY);
        if(StringUtils.isBlank(cache)){
            return list;
        }
        list = JsonHelper.jsonArrayStr2List(cache, Fquotes.class);
        return list;
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
