package com.jucaifu.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.shiro.codec.Hex;

/**
 * UrlHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/11.
 */
public class UrlHelper {
    private UrlHelper() {
    }

    /**
     * Encode string.
     *
     * @param s   the s
     * @param enc the enc
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String encode(String s, String enc) throws UnsupportedEncodingException {

        return URLEncoder.encode(s, enc);
    }

    /**
     * Decode string.
     *
     * @param s   the s
     * @param enc the enc
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String decode(String s, String enc) throws UnsupportedEncodingException {

        return URLDecoder.decode(s, enc);
    }

    /**
     * Decode string.
     *
     * @param str the str
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String decode(String str) throws UnsupportedEncodingException {

        return decode(str, "UTF-8");
    }


    /**
     * Encode string.
     *
     * @param str the str
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String encode(String str) throws UnsupportedEncodingException {

        return encode(str, "UTF-8");
    }


    /**
     * 说明：主要提供给调试的时候使用
     * <p>
     * Encode obj.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static <T> String encodeObj(T obj) throws UnsupportedEncodingException {

        if (obj != null) {

            String encodeQueryJson = UrlHelper.encode(JsonHelper.obj2JsonStr(obj));
            return encodeQueryJson;

        } else {
            return null;
        }
    }

    /**
     * Encode obj 2 hex str.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the string
     */
    public static <T> String encodeObj2HexStr(T obj) {

        String encodeQueryJson = null;

        if (obj != null) {

            String jsonStr = JsonHelper.obj2JsonStr(obj);
            if (jsonStr != null) {
                encodeQueryJson = Hex.encodeToString(jsonStr.getBytes());
            }
        }
        return encodeQueryJson;
    }

    /**
     * Dencode hex str.
     *
     * @param hexStr the hex str
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static String dencodeHexStr(String hexStr) throws IllegalArgumentException {

        String encodeQueryJson = null;

        if (hexStr != null) {
            byte[] data = Hex.decode(hexStr);
            encodeQueryJson = StringUtils.newStringUtf8(data);
        }

        return encodeQueryJson;
    }

}
