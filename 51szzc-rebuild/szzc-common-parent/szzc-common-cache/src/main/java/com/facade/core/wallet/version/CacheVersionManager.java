package com.facade.core.wallet.version;

import java.util.HashMap;
import java.util.Map;

/**
 * CacheVersionManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 16/1/19.
 */
public class CacheVersionManager implements CacheVersion {

    private static Map<Integer, Class<?>> sVersionParserMap = new HashMap<>();

    /**
     * Gets parse class.
     *
     * @param v the v
     * @return the parse class
     */
    public static Class<?> getParseClass(Integer v) {
        return sVersionParserMap.get(v);
    }

}
