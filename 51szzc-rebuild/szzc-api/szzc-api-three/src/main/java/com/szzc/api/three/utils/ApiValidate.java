package com.szzc.api.three.utils;

import com.facade.core.wallet.cache.UserCacheManager;
import com.jucaifu.common.log.LOG;
import com.szzc.common.api.exceptions.TokenInvalidException;
import com.szzc.common.api.packet.app.request.AppApiRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * ApiValidate
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/3.
 */
public class ApiValidate {

    /**
     * Validate token.
     *
     * @param appApiRequest the app api request
     * @return the string
     */
    public static String validateToken(AppApiRequest appApiRequest) {
        String token = null;
        try {
            token = appApiRequest.getHeader().getToken();
        } catch (Exception e) {
            LOG.e(ApiValidate.class, "AppApiRequest", e);
        }
        if (token == null) {
            throw new TokenInvalidException();
        }

        return validateToken(token);
    }

    /**
     * Validate token.
     *
     * @param token the token
     * @return the string
     */
    public static String validateToken(String token) {
        String uid = UserCacheManager.getUidWithToken(token);
        if (StringUtils.isBlank(uid)) {
            throw new TokenInvalidException();
        }
        return uid;
    }

}
