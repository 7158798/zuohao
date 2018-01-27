package com.jucaifu.common.util;

import java.util.UUID;

/**
 * UUIDHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/25.
 */
public class UUIDHelper {

    private UUIDHelper() {
    }


    /**
     * Get UUID.
     *
     * @return the string
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Gets 32 UUID.
     *
     * @return the 32 UUID.
     */
    public static String get32UUID() {
        String uuid = getUUID().trim().replaceAll("-", "");
        return uuid;
    }

}
