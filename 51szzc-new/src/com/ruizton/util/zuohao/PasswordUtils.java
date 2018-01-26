package com.ruizton.util.zuohao;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created by lx on 17-3-30.
 */
public class PasswordUtils {

    public static final String ALGORITHM = "PBEWITHMD5andDES";

    public static final int ITERATION_COUNT = 100;

    /**
     * 初始盐<br/>
     * 盐的长度必须为8位
     * @return byte[] 盐
     * @throws Exception
     */
    public static byte[] initSalt() throws Exception{
        //实例化安全随机数
        SecureRandom random = new SecureRandom();
        //产出盐
        return random.generateSeed(8);
    }

    /**
     * 转换密钥
     *
     * @param password  密码
     * @return Key 密钥
     */
    private static Key toKey(String password) throws Exception{
        //密钥材料
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        //生成密钥
        return keyFactory.generateSecret(keySpec);
    }

    /**
     * 加密
     *
     * @param data  待加密数据
     * @param key   密钥
     * @param salt  盐
     * @return byte[]   加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data  待机密数据
     * @param key   密钥
     * @param salt  盐
     * @return byte[]   解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data,String password,byte[] salt)throws Exception{
        //转换密钥
        Key key = toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
        //实例化
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    private static String  showByteArray(byte[] data){
        if(null == data){
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for(byte b:data){
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();

    }

    public static void main(String[] args) throws Exception {

        ///byte[] salt =decryptBASE64("1232143");
        byte[] salt = initSalt();
        String encrySalt = encryptBASE64(salt).trim();
        System.out.println("salt："+ salt);
        System.out.println("salt："+ String.valueOf(salt).getBytes());
        System.out.println("salt："+ encrySalt);
        //这里的password需要是ASCII码，不然会报异常
        String password = stringToAscii(encrySalt);//"1111";
        System.out.println("口令："+password);

        String data ="1314";
        System.out.println("加密前数据: string:"+data);
        //System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));
        byte[] encryptData = encrypt(data.getBytes(), password,salt);
        String miwen = encryptBASE64(encryptData).trim();
        System.out.println("密文" + miwen);

        byte[] temp = decryptBASE64(miwen);
        byte[] decryptData = decrypt(temp, password,decryptBASE64(encrySalt));
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));
        System.out.println("解密后数据: string:"+String.valueOf(decryptData));
        System.out.println("解密后数据: string:"+new String(decryptData));

    }


    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
