package com.jucaifu.common.security;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Base64Helper : 用Apache的common codes实现
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/13.
 */
public class Base64Helper {

    /**
     * Encode byte [ ].
     *
     * @param binaryData the binary data
     * @return the byte [ ]
     */
    public static byte[] encode(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    /**
     * Encode byte [ ].
     *
     * @param src the src
     * @return the byte [ ]
     */
    public static byte[] encode(String src) {
        if (src != null) {
            return encode(StringUtils.getBytesUtf8(src));
        } else {
            return null;
        }
    }

    /**
     * Encode 2 str.
     *
     * @param src the src
     * @return the string
     */
    public static String encode2Str(String src) {
        byte[] bytes = encode(src);
        if (src != null) {
            return new String(bytes);
        } else {
            return null;
        }
    }

    /**
     * Encode 2 hex str.
     *
     * @param src the src
     * @return the string
     */
    public static String encode2HexStr(String src) {
        byte[] bytes = encode(src);
        if (src != null) {
            return Hex.encodeHexString(bytes);
        } else {
            return null;
        }
    }


    /**
     * Decode byte [ ].
     *
     * @param base64Data the base 64 data
     * @return the byte [ ]
     */
    public static byte[] decode(byte[] base64Data) {
        return Base64.decodeBase64(base64Data);
    }

    /**
     * Decode byte [ ].
     *
     * @param src the src
     * @return the byte [ ]
     */
    public static byte[] decode(String src) {
        return Base64.decodeBase64(src);
    }

    /**
     * Decode hex str.
     *
     * @param src the src
     * @return the byte [ ]
     * @throws DecoderException the decoder exception
     */
    public static byte[] decodeHexStr(String src) throws DecoderException {

        byte[] bytes = Hex.decodeHex(src.toCharArray());
        return Base64.decodeBase64(bytes);
    }

    /**
     * Decode 2 str.
     *
     * @param src the src
     * @return the string
     */
    public static String decode2Str(String src) {

        byte[] bytes = decode(src);
        if (bytes != null) {
            return StringUtils.newStringUtf8(bytes);
        } else {
            return null;
        }
    }

    /**
     * Decode hex str 2 str.
     *
     * @param src the src
     * @return the string
     * @throws DecoderException the decoder exception
     */
    public static String decodeHexStr2Str(String src) throws DecoderException {

        byte[] bytes = decodeHexStr(src);
        if (bytes != null) {
            return StringUtils.newStringUtf8(bytes);
        } else {
            return null;
        }
    }

}
