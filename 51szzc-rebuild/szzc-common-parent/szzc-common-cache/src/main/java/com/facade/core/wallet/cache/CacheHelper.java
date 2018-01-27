package com.facade.core.wallet.cache;


import com.facade.core.wallet.token.TokenFactory;

import java.io.Serializable;
import java.util.Set;

/**
 * CacheHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/12.
 */
public class CacheHelper {

    /**
     * Keys set.
     *
     * @param pattern the pattern
     * @return the set
     */
    public static Set<byte[]> keys(String pattern) {
        return RedisManager.keys(pattern);
    }

    /**
     * Gets obj.
     *
     * @param key the key
     * @return the obj
     */
    public static <T> T getObj(String key) {
        return RedisSessionManager.getSessionObj(key);
    }

    /**
     * Save obj.
     *
     * @param <T> the type parameter
     * @param key the key
     * @param obj the obj
     * @return the boolean
     */
    public static <T extends Serializable> boolean saveObj(String key, T obj) {
        return RedisSessionManager.saveSession(key, obj);
    }

    /**
     * Save obj.
     *
     * @param <T>     the type parameter
     * @param key     the key
     * @param obj     the obj
     * @param seconds the seconds
     * @return the boolean
     */
    public static <T extends Serializable> boolean saveObj(String key, T obj, Integer seconds) {
        return RedisSessionManager.saveSession(key, obj, seconds);
    }

    /**
     * Delete obj.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean deleteObj(String key) {
        return RedisSessionManager.deleteSession(key);
    }

    /**
     * Build test token.
     *
     * @param uid the uid
     * @return the string
     */
    public static String buildTestToken(String uid) {
        String token = TokenFactory.generatorUserToken() + "001";
        RedisSessionManager.saveSession(token, uid);
        return token;
    }


}
