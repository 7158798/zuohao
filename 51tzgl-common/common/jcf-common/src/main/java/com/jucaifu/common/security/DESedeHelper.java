package com.jucaifu.common.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

/**
 * DESedeHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/28.
 */
public class DESedeHelper {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 支持PKCS5PADDING填充方式
     * Bouncy Castle 支持 PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param keyData 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] keyData) throws Exception {

        DESedeKeySpec dks = new DESedeKeySpec(keyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        return secretKey;
    }


    /**
     * encrypt
     *
     * @param keyData 密钥
     * @param data    待加密数据
     * @return byte[] 加密数据
     * @throws Exception the exception
     */
    public static byte[] encrypt(byte[] keyData, byte[] data) throws Exception {

        Key key = toKey(keyData);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param keyData 密钥
     * @param data    密文数据
     * @return byte[] 解密数据
     * @throws Exception the exception
     */
    public static byte[] decrypt(byte[] keyData, byte[] data) throws Exception {

        Key key = toKey(keyData);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }


    /**
     * Generate bytes key.
     *
     * @return byte[] 二进制密钥
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static byte[] generateBytesKey() throws NoSuchAlgorithmException {

        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        kg.init(168);//DESede 要求密钥长度为 112位或168位

        SecretKey secretKey = kg.generateKey();

        return secretKey.getEncoded();
    }

    /**
     * Generate hex str key.
     *
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static String generateHexStrKey() throws NoSuchAlgorithmException {

        return Hex.encodeHexString(generateBytesKey());
    }

    /**
     * Encrypt string.
     *
     * @param keyStr the key str
     * @param str    the str
     * @return the string
     * @throws Exception the exception
     */
    public static String encrypt(String keyStr, String str) throws Exception {

        String encryptStr = null;

        if (str != null && keyStr != null) {

            byte[] keyData = Hex.decodeHex(keyStr.toCharArray());
            byte[] data = StringUtils.getBytesUtf8(str);

            byte[] encryptData = encrypt(keyData, data);

            encryptStr = Hex.encodeHexString(encryptData);
        }

        return encryptStr;
    }

    /**
     * Decrypt string.
     *
     * @param keyStr     the key str
     * @param encryptStr the encrypt str
     * @return the string
     * @throws Exception the exception
     */
    public static String decrypt(String keyStr, String encryptStr) throws Exception {

        String decryptStr = null;

        if (keyStr != null && encryptStr != null) {
            byte[] keyData = Hex.decodeHex(keyStr.toCharArray());
            byte[] encryptData = Hex.decodeHex(encryptStr.toCharArray());
            byte[] data = decrypt(keyData, encryptData);

            decryptStr = StringUtils.newStringUtf8(data);
        }

        return decryptStr;
    }
}
