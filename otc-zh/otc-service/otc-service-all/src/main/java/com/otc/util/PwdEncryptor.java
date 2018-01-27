package com.otc.util;

/**
 * Created by a123 on 17-4-20.
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PwdEncryptor {

    /**
     * 对密码进行加密<br />
     * 使用SHA进行摘要，再进行Base64
     *
     * @param clearTextPassword 明文密码
     * @return 密文密码
     */
    public static String encrypt(String clearTextPassword) {
        byte[] raw = null;
        try {
            raw = digestRaw(clearTextPassword);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }
        return encode(raw);
    }

    private static byte[] digestRaw(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(text.getBytes("UTF-8"));
        return messageDigest.digest();
    }

    private static String encode(byte raw[]) {
        return encode(raw, 0, raw.length);
    }

    private static String encode(byte raw[], int offset, int length) {
        int lastIndex = Math.min(raw.length, offset + length);

        StringBuilder sb = new StringBuilder(((lastIndex - offset) / 3 + 1) * 4);

        for (int i = offset; i < lastIndex; i += 3) {
            sb.append(encodeBlock(raw, i, lastIndex));
        }

        return sb.toString();
    }

    private static char[] encodeBlock(byte raw[], int offset, int lastIndex) {
        int block = 0;
        int slack = lastIndex - offset - 1;
        int end = slack < 2 ? slack : 2;

        for (int i = 0; i <= end; i++) {
            byte b = raw[offset + i];

            int neuter = b >= 0 ? ((int) (b)) : b + 256;
            block += neuter << 8 * (2 - i);
        }

        char base64[] = new char[4];

        for (int i = 0; i < 4; i++) {
            int sixbit = block >>> 6 * (3 - i) & 0x3f;
            base64[i] = getChar(sixbit);
        }

        if (slack < 1) {
            base64[2] = '=';
        }

        if (slack < 2) {
            base64[3] = '=';
        }

        return base64;
    }

    private static char getChar(int sixbit) {
        if ((sixbit >= 0) && (sixbit <= 25)) {
            return (char) (65 + sixbit);
        }

        if ((sixbit >= 26) && (sixbit <= 51)) {
            return (char) (97 + (sixbit - 26));
        }

        if ((sixbit >= 52) && (sixbit <= 61)) {
            return (char) (48 + (sixbit - 52));
        }

        if (sixbit == 62) {
            return '+';
        }

        return sixbit != 63 ? '?' : '/';
    }
}

