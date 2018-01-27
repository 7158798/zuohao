package com.otc.api.web.utils;

import com.alibaba.fastjson.JSONObject;
import com.jucaifu.common.constants.WebConstant;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.JsonHelper;
import com.jucaifu.common.util.UrlHelper;
import com.otc.common.api.exceptions.TokenInvalidException;
import com.otc.common.api.packet.web.request.WebApiBaseReq;
import com.otc.core.cache.UserCacheManager;

import java.io.UnsupportedEncodingException;

/**
 * WebTokenValidate
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/12/3.
 */
public class WebTokenValidate {

    /**
     * Validate token.
     *
     * @param <T> the type parameter
     * @param req the req bean
     * @return the string
     */
    public static <T extends WebApiBaseReq> Long validateToken(T req) {

        Long uid = null;

        if (req != null) {
            uid = validateToken(req.getToken());
        } else {
            throw new TokenInvalidException();
        }

        return uid;
    }


    /**
     * Validate token.
     *
     * @param token the token
     * @return the string
     */
    public static Long validateToken(String token) {
        Long id = UserCacheManager.getUidWithToken(token);
        if (id == null) {
            throw new TokenInvalidException();
        }
        return id;
    }

    /**
     * Validate query json.
     *
     * @param queryJson the query json
     * @return the string
     */
    public static Long validateQueryJson(String queryJson) {
        try {
            queryJson = UrlHelper.decode(queryJson);
        } catch (UnsupportedEncodingException e) {
        }
        try {
            JSONObject jsonObj = JsonHelper.jsonStr2JsonObj(queryJson);
            LOG.d("", jsonObj);
            String token = jsonObj.getString(WebConstant.KEY_TOKEN);
            return validateToken(token);
        } catch (Exception e) {
            LOG.e("", "validateQueryJson", e);
            throw new TokenInvalidException();
        }
    }

}
