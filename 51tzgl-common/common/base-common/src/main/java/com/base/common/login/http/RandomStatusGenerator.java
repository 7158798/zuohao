package com.base.common.login.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by zh on 16-10-7.
 */
public class RandomStatusGenerator {
    private static String valueBeforeMD5 = "";
    private static String valueAfterMD5 = "";
    private static Random myRand;
    private static SecureRandom mySecureRand = new SecureRandom();
    private static String s_id;
    private static final int PAD_BELOW = 16;
    private static final int TWO_BYTES = 255;

    public RandomStatusGenerator() {
    }

    public RandomStatusGenerator(boolean secure) {
    }

    public static String getUniqueState() {
        if(valueAfterMD5.equals("")) {
            getRandomGUID(false);
        }

        return valueAfterMD5;
    }

    private static void getRandomGUID(boolean secure) {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer(128);

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var11) {
            ;
        }

        try {
            long e = System.currentTimeMillis();
            long rand = 0L;
            if(secure) {
                rand = mySecureRand.nextLong();
            } else {
                rand = myRand.nextLong();
            }

            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(e));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));
            valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());
            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer(32);

            for(int j = 0; j < array.length; ++j) {
                int b = array[j] & 255;
                if(b < 16) {
                    sb.append('0');
                }

                sb.append(Integer.toHexString(b));
            }

            valueAfterMD5 = sb.toString();
        } catch (Exception var12) {
            ;
        }

    }

    public String toString() {
        String raw = valueAfterMD5.toUpperCase();
        StringBuffer sb = new StringBuffer(64);
        sb.append(raw.substring(0, 8));
        sb.append("-");
        sb.append(raw.substring(8, 12));
        sb.append("-");
        sb.append(raw.substring(12, 16));
        sb.append("-");
        sb.append(raw.substring(16, 20));
        sb.append("-");
        sb.append(raw.substring(20));
        return sb.toString();
    }

    static {
        long secureInitializer = mySecureRand.nextLong();
        myRand = new Random(secureInitializer);

        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
        }

    }
}
