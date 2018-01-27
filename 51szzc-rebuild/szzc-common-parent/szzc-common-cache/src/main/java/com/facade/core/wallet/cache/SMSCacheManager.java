package com.facade.core.wallet.cache;

import java.io.Serializable;

/**
 * SMSCacheManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/12.
 */
public class SMSCacheManager {

    /**
     * Save sms obj.
     *
     * @param <T>     the type parameter
     * @param key     the key
     * @param smsObj  the sms obj
     * @param seconds the seconds
     * @return the boolean
     */
    public static <T extends Serializable> boolean saveSmsObj(String key, T smsObj, Integer seconds) {

        return CacheHelper.saveObj(key, smsObj, seconds);
    }

    /**
     * Gets sms obj.
     *
     * @param key the key
     * @return the sms obj
     */
    public static <T> T getSmsObj(String key) {
        return CacheHelper.getObj(key);
    }

    /**
     * Delete sms obj.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean deleteSmsObj(String key) {
        return CacheHelper.deleteObj(key);
    }
    
}
