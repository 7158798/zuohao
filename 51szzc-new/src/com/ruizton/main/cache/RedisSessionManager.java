package com.ruizton.main.cache;



import com.ruizton.main.constants.TimeConstant;
import com.ruizton.main.log.LOG;

import java.io.Serializable;

/**
 * RedisSessionManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/19.
 */
public final class RedisSessionManager {

    private static final void e(String msg, Throwable ex) {
        LOG.e(RedisSessionManager.class, msg, ex);
    }

    /**
     * Session默认过期时间
     */
    private static final int DEFAULT_SESSION_TIMEOUT = 15 * TimeConstant.ONE_DAY_UNIT_SECONDS;

    /**
     * Save session.持久化存储
     *
     * @param <T>        the type parameter
     * @param sessionId  the session id
     * @param sessionObj the session obj
     * @return the boolean
     */
    public static <T extends Serializable> boolean saveSession(String sessionId, T sessionObj) {
        return saveSession(sessionId, sessionObj, -1);
    }

    /**
     * Save session.
     *
     * @param <T>        the type parameter
     * @param sessionId  the session id
     * @param sessionObj the session obj
     * @param seconds    如果为null，使用默认超时时间
     * @return the boolean
     */
    public static <T extends Serializable> boolean saveSession(String sessionId, T sessionObj, Integer seconds) {

        boolean result = false;

        Integer cachedSecondes = seconds;

        if (cachedSecondes == null) {
            cachedSecondes = DEFAULT_SESSION_TIMEOUT;
        }
        try {
            result = RedisManager.save(sessionId, sessionObj, cachedSecondes);
        } catch (Exception e) {
            e("缓存删除失败：", e);
        }
        return result;
    }


    /**
     * Update session time.
     *
     * @param sessionId the session id
     * @param seconds   the seconds
     * @return the boolean
     */
    public static boolean updateSessionTime(String sessionId, Integer seconds) {
        boolean result = false;

        Integer cachedSecondes = seconds;
        if (cachedSecondes == null) {
            cachedSecondes = DEFAULT_SESSION_TIMEOUT;
        }

        try {
            result = RedisManager.expire(sessionId, cachedSecondes);
        } catch (Exception e) {
            e("Session缓存时间更新失败：", e);
        }
        return result;
    }


    /**
     * Delete session.
     *
     * @param sessionId the session id
     * @return the boolean
     */
    public static boolean deleteSession(String sessionId) {
        boolean result = false;
        try {
            result = RedisManager.del(sessionId);
        } catch (Exception e) {
            e("缓存删除失败：", e);
        }
        return result;
    }

    /**
     * Gets session obj.
     *
     * @param sessionId the session id
     * @return the session obj
     */
    public static <T> T getSessionObj(String sessionId) {
        Object sessionObj = null;
        try {
            sessionObj = RedisManager.get(sessionId);
            LOG.d(RedisSessionManager.class, "sessionObj:" + sessionObj);
        } catch (Exception e) {
            e("缓存读取失败：", e);
        }
        return (T) sessionObj;
    }

}
