package com.otc.core.cache;

import com.jucaifu.common.configs.TimeOutConfigConstant;
import com.jucaifu.common.log.LOG;
import com.otc.core.pojo.PushDevicePo;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 用户缓存管理
 * UserCacheManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/23.
 */
public class UserCacheManager {

    private static void i(String msg) {
        LOG.i(UserCacheManager.class, msg);
    }

    /**
     * 根据Token获取用户id
     *
     * @param token the token
     * @return the uid with token
     */
    public static Long getUidWithToken(String token) {

        String uid = null;

        if (token != null) {
            Object obj = RedisSessionManager.getSessionObj(token);
            if (obj != null && obj instanceof String) {
                uid = (String) obj;
            } else {
                if (obj != null) {
                    i("调用缓存Api:用户缓存出现错误-token对应的value不是string类型");
                } else {
                    i("调用缓存Api:token已经失效");
                }
            }
        }
        return uid == null ?null:Long.valueOf(uid);
    }


    /**
     * 获取用户信息
     * Gets cache console info.
     *
     * @param uid the uid
     * @return the cache console info
     */
    public static UserCache getUserCache(String uid) {

        UserCache userCache = null;

        if (uid != null) {
            Object obj = RedisSessionManager.getSessionObj(uid);
            if (obj instanceof UserCache) {
                userCache = (UserCache) obj;
            } else {
                if (userCache != null) {
                    i("调用缓存Api:用户缓存数据出现错误-uid对应的value不是UserCache类型");
                } else {
                    i("调用缓存Api:不存在用户缓存数据");
                }
            }
        } else {
            i("调用缓存Api:uid==null");
        }

        return userCache;
    }

    /**
     * Gets push device list.
     *
     * @param uid the uid
     * @return the push device list
     */
    public static Collection<PushDevicePo> getPushDeviceList(String uid) {

        Collection<PushDevicePo> list = null;

        UserCache userCache = getUserCache(uid);
        if (userCache != null) {
            HashMap<String, PushDevicePo> map = userCache.getPushDeviceMap();
            list = map.values();

            LOG.d("", "用户id: " + uid + " 绑定的PushDevice count = " + list.size());

        } else {
            LOG.d("", "用户id: " + uid + " 没有有效的token数据");
        }

        return list;
    }

    /**
     * Bind push device.
     *
     * @param token      the token
     * @param deviceType the device type
     * @param deviceId   the device id
     * @return the boolean
     */
    public static boolean bindPushDevice(String token, String deviceType, String deviceId) {

        boolean result = false;

        Long id = getUidWithToken(token);
        String uid = null;
        if(id != null){
            uid = String.valueOf(id);
        }

        if (uid != null) {

            UserCache userCache = getUserCache(uid);

            if (userCache != null) {

                if (userCache.bindPushDevice(token, deviceType, deviceId)) {

                    result = RedisSessionManager.saveSession(uid, userCache);

                    if (result) {
                        i("更新uid-pushDevice关系 uid:" + uid + "成功");
                    } else {
                        i("更新uid-pushDevice关系 uid:" + uid + "失败");
                    }
                }

            }
        }

        return result;
    }

    /**
     * Un bind push device.
     *
     * @param token the token
     * @return the boolean
     */
    public static boolean unBindPushDevice(String token) {

        boolean result = false;

        Long id = getUidWithToken(token);
        String uid = null;
        if(id != null){
            uid = String.valueOf(id);
        }

        if (uid != null) {

            UserCache userCache = getUserCache(uid);

            if (userCache != null) {

                HashMap<String, PushDevicePo> map = userCache.getPushDeviceMap();

                Collection<PushDevicePo> collection = map.values();

                for (PushDevicePo po : collection) {
                    if (po.getToken().equals(token)) {
                        String deviceId = po.getDeviceId();
                        map.remove(deviceId);
//                        userCache.setPushDeviceMap(map);
                        result = RedisSessionManager.saveSession(uid, userCache);
                        break;
                    }
                }
            }
        }

        if (result) {
            i("解除绑定uid-pushDevice关系 uid:" + uid + "成功");
        } else {
            i("解除绑定uid-pushDevice关系 uid:" + uid + "失败");
        }
        return result;
    }

    /**
     * Gets cache obj.
     *
     * @param uid the uid
     * @return the cache obj
     */

    public static <T extends Serializable> T getCacheObj(String uid) {

        T obj = null;

        UserCache<T> userCache = getUserCache(uid);

        if (userCache != null) {
            obj = userCache.getCacheObj();
        }

        return obj;
    }

    public static <T extends Serializable> T getCacheObj(Long id) {
        String uid = String.valueOf(id);
        return getCacheObj(uid);
    }


    /**
     * Gets token set.
     *
     * @param uid the uid
     * @return the token set
     */
    public static HashSet<String> getTokenSet(String uid) {

        HashSet<String> tokenSet = null;

        UserCache userCache = getUserCache(uid);

        if (userCache != null) {
            tokenSet = userCache.getTokenSet();
        } else {
            i("用户没有缓存信息: uid= " + uid);
        }

        return tokenSet;
    }

    /**
     * Bind console token.
     *
     * @param <T>          the type parameter
     * @param token        the token
     * @param uid          the uid
     * @param obj          the obj
     * @param cacheSeconds the cache seconds
     * @return the boolean
     */
    public static <T extends Serializable> boolean bindUserToken(String token, String uid, T obj, Integer cacheSeconds) {

        boolean result = false;

        if (token == null || uid == null) {
            i("错误的调用用户缓存操作:bindUserToken ［参数为空］");
            return result;
        }

        result = RedisSessionManager.saveSession(token, uid, cacheSeconds);

        if (result) {
            i("建立token-uid关系 uid:" + uid + "成功");

            UserCache userCache = getUserCache(uid);
            if (userCache == null) {
                userCache = UserCache.newInstance(uid);
            }
            userCache.setCacheObj(obj);

            HashSet<String> tokenSet = userCache.getTokenSet();
            tokenSet.add(token);

            //清理无效的token
            HashSet<String> needRemoveSet = new HashSet<>();
            for (String t : tokenSet) {
                if (!RedisManager.exists(t)) {
                    needRemoveSet.add(t);
                }
            }
            tokenSet.removeAll(needRemoveSet);
            i("清理无效的token数量＝" + needRemoveSet.size());

            userCache.setTokenSet(tokenSet);
            i("有效的token数量＝" + tokenSet.size());

            result = RedisSessionManager.saveSession(uid, userCache);
            if (result) {
                i("更新uid-tokenset关系 uid:" + uid + "成功");
            } else {
                i("更新uid-tokenset关系 uid:" + uid + "失败");
            }

        } else {
            i("建立token-uid关系 uid:" + uid + "失败");
        }


        return result;
    }

    /**
     * Bind console token with obj.
     *
     * @param <T>   the type parameter
     * @param token the token
     * @param uid   the uid
     * @param obj   the obj
     * @return the boolean
     */
    public static <T extends Serializable> boolean bindUserTokenWithObj(String token, Long uid, T obj) {

        return bindUserToken(token, String.valueOf(uid), obj, TimeOutConfigConstant.USER_TOKEN_TIMEOUT);
    }


    /**
     * Delete cache obj.
     *
     * @param id the uid
     * @return the boolean
     */
    public static boolean deleteCacheObj(Long id) {

        boolean result = false;

        if (id != null) {
            String uid = String.valueOf(id);
            UserCache userCache = null;

            Object obj = RedisSessionManager.getSessionObj(uid);

            if (obj instanceof UserCache) {
                userCache = (UserCache) obj;
                userCache.setCacheObj(null);

                result = RedisSessionManager.saveSession(uid, userCache);

            } else {
                if (userCache != null) {
                    i("调用缓存Api:用户缓存数据出现错误-uid对应的value不是UserCache类型");
                } else {
                    i("调用缓存Api:不存在用户缓存数据");
                }
            }
        } else {
            i("调用缓存Api:清理用户信息 uid==null");
        }

        return result;
    }

    /**
     * Delete all cache.
     *
     * @param id the uid
     * @return the boolean
     */
    public static boolean deleteAllCache(Long id) {

        boolean result = false;


        if (id != null) {
            String uid = String.valueOf(id);
            UserCache userCache = null;

            Object obj = RedisSessionManager.getSessionObj(uid);

            if (obj instanceof UserCache) {
                userCache = (UserCache) obj;
                HashSet<String> tokenSet = userCache.getTokenSet();

                for (String t : tokenSet) {
                    result = RedisSessionManager.deleteSession(t);
                    if (!result) {
                        i("清理用户Token失败:token=" + t);
                    }
                }

                result = RedisSessionManager.deleteSession(uid);

            } else {
                if (userCache != null) {
                    i("调用缓存Api:用户缓存数据出现错误-uid对应的value不是UserCache类型");
                } else {
                    i("调用缓存Api:不存在用户缓存数据");
                }
            }
        } else {
            i("调用缓存Api:清理用户信息 uid==null");
        }

        return result;
    }

    /**
     * Validate and refresh with token.
     *
     * @param token the token
     * @return the boolean
     */
    public static boolean validateAndRefreshWithToken(String token) {
         return validateAndRefreshWithToken(token,null);
    }

    public static boolean validateAndRefreshWithToken(String token,Integer cacheSeconds){
        boolean result = false;

        Long id = getUidWithToken(token);
        String uid = null;
        if(id != null){
            uid = String.valueOf(id);
        }

        if(cacheSeconds == null){
            cacheSeconds = TimeOutConfigConstant.USER_TOKEN_TIMEOUT;
        }

        if (uid != null) {
            result = RedisSessionManager.updateSessionTime(token, cacheSeconds);
        }

        return result;
    }

    /**
     * Log out with token.
     *
     * @param token the token
     * @return the boolean
     */
    public static boolean logOutWithToken(String token) {

        boolean result = false;

        Long id = getUidWithToken(token);
        String uid = null;
        if(id != null){
            uid = String.valueOf(id);
        }

        if (uid != null) {

            result = RedisSessionManager.deleteSession(token);
            if (result) {
                i("用户注销成功 token:" + token);
            } else {
                i("用户注销失败 token:" + token);
            }

            UserCache userCache = null;

            Object obj = RedisSessionManager.getSessionObj(uid);

            if (obj instanceof UserCache) {
                userCache = (UserCache) obj;
                HashSet<String> tokenSet = userCache.getTokenSet();

                //清理无效的token
                HashSet<String> needRemoveSet = new HashSet<>();
                for (String t : tokenSet) {
                    if (!RedisManager.exists(t)) {
                        needRemoveSet.add(t);
                    }
                }
                tokenSet.removeAll(needRemoveSet);
                i("清理无效的token数量＝" + needRemoveSet.size());

                userCache.setTokenSet(tokenSet);
                i("有效的token数量＝" + tokenSet.size());

                RedisSessionManager.saveSession(uid, userCache);

            } else {
                if (userCache != null) {
                    i("调用缓存Api:用户缓存数据出现错误-uid对应的value不是UserCache类型");
                } else {
                    i("调用缓存Api:不存在用户缓存数据");
                }
            }
        } else {
            i("调用缓存Api:清理用户信息 uid==null");
        }

        return result;
    }

    /**
     * 绑定uid与用户userId
     *
     * @param uid
     * @param obj
     * @return
     */
    public static <T extends Serializable> boolean bindUserIdWithUid(String uid, T obj) {
        boolean reuslt = false;

        if (StringUtils.isNotBlank(uid) && obj != null) {
            reuslt = RedisSessionManager.saveSession(uid, obj, TimeOutConfigConstant.BIND_UID_USERID_TIMEOUT);
        } else {
            i("调用缓存Api:绑定uid与用户userId关系 uid为空或obj为空");
        }
        return reuslt;
    }

    /**
     * Gets bind obj.
     *
     * @param uid the uid
     * @return the bind obj
     */

    public static <T extends Serializable> T getBindObj(String uid) {

        return RedisSessionManager.getSessionObj(uid);
    }
}
