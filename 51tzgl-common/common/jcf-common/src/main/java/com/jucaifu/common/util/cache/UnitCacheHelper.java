package com.jucaifu.common.util.cache;

import java.util.HashMap;

/**
 * UnitCacheHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/17.
 * =====================================================
 */
public class UnitCacheHelper {

    private HashMap<String, Object> cachMap;

    /**
     * Instantiates a new Unit cache helper.
     */
    public UnitCacheHelper() {
        cachMap = new HashMap<String, Object>();
    }

    /**
     * Put void.
     *
     * @param key the key
     * @param value the value
     */
    public synchronized void put(String key, Object value) {
        cachMap.put(key, value);
    }

    /**
     * Get t.
     *
     * @param <T>  the type parameter
     * @param key the key
     * @return the t
     */
    public synchronized <T> T get(String key) {
        return (T) cachMap.get(key);
    }

    /**
     * Quick put.
     *
     * @param key the key
     * @param value the value
     */
    public void quickPut(String key, Object value) {
        cachMap.put(key, value);
    }

    /**
     * Quick get.
     *
     * @param <T>  the type parameter
     * @param key the key
     * @return the t
     */
    public <T> T quickGet(String key) {
        return (T) cachMap.get(key);
    }

}
