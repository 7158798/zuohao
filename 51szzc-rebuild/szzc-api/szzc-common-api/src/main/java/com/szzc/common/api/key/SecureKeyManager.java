package com.szzc.common.api.key;

import java.util.HashMap;
import java.util.Map;

/**
 * SecureKeyManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/19.
 */
public class SecureKeyManager implements KeyVersion {

    private static final Map<String, String> sKeyMap = new HashMap<>();

    static {
        sKeyMap.put(KEY_V1, "1aa1b0d5efadd9ef79c1704ff249376ea7c70bcecb4c23c1");
    }

    /**
     * Gets common key.
     *
     * @return the common key
     */
    public static String getCommonKey() {

        //TODO:后期独立密钥管理模块
        return sKeyMap.get(KEY_V1);
    }

    /**
     * Gets user key.
     *
     * @param uid the uid
     * @return the user key
     */
    public static String getUserKey(String uid) {

        //TODO:后期独立密钥管理模块
        return sKeyMap.get(KEY_V1);
    }
}
