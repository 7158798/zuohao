package com.jucaifu.common.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * MD5Helper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/27.
 */
public class MD5Helper {

    /**
     * Encode md5.
     *
     * @param data the data
     * @return the byte [ ]
     */
    public static byte[] encodeMD5(String data) {

        return DigestUtils.md5(data);
    }

    /**
     * Encode md5 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String encodeMD5Hex(String data) {

        return DigestUtils.md5Hex(data);
    }


    /**
     * Validate sign.
     *
     * @param src  the src
     * @param sign the sign
     * @return the boolean
     */
    public static boolean validateSign(String src, String sign) {

        boolean success = false;

        if (src != null && sign != null) {
            String signSrc = MD5Helper.encodeMD5Hex(src);
            if (signSrc != null && signSrc.equals(sign)) {
                success = true;
            }
        }

        return success;
    }

    /**
     * Md 5 hash.
     *
     * @param source         the source
     * @param salt           the salt
     * @param hashIterations the hash iterations
     * @return the string
     */
    public static String md5Hash(Object source, Object salt, int hashIterations) {
        // 第一个参数：明文，原始密码
        // 第二个参数：盐，通过使用随机数
        // 第三个参数：散列的次数，比如散列两次，相当 于md5(md5(''))
        Md5Hash md5Hash = new Md5Hash(source, salt, hashIterations);
        return md5Hash.toString();
    }

}
