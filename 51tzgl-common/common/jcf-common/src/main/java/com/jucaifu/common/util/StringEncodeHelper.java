package com.jucaifu.common.util;


import java.io.UnsupportedEncodingException;

/**
 * StringEncodeHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/8/28.
 */
public class StringEncodeHelper {

    private StringEncodeHelper() {
    }

    /**
     * The enum Encode convert type.
     */
    public static enum EncodeConvertType {

        /**
         * The UTF8_TO_ISO88591.
         */
        UTF8_TO_ISO88591("UTF-8", "ISO-8859-1"),

        /**
         * The ISO88591_TO_UTF8.
         */
        ISO88591_TO_UTF8("ISO-8859-1", "UTF-8"),

        /**
         * The GBK_TO_ISO88591.
         */
        GBK_TO_ISO88591("GBK", "ISO-8859-1"),

        /**
         * The ISO88591_TO_GBK.
         */
        ISO88591_TO_GBK("ISO-8859-1", "GBK"),;
        /**
         * The From encode.
         */
        private String fromEncode;
        /**
         * The To encode.
         */
        private String toEncode;

        /**
         * Instantiates a new Encode convert type.
         *
         * @param fromEncode the from encode
         * @param toEncode   the to encode
         */
        EncodeConvertType(String fromEncode, String toEncode) {
            this.fromEncode = fromEncode;
            this.toEncode = toEncode;
        }
    }


    /**
     * Convert string.
     *
     * @param str        the str
     * @param fromEncode the from encode
     * @param toEncode   the to encode
     * @return the string
     */
    public static String convert(String str, String fromEncode, String toEncode) {

        String ret = "";

        try {
            ret = new String(str.getBytes(fromEncode), toEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;
    }


    /**
     * Convert string.
     *
     * @param str  the str
     * @param type the type
     * @return the string
     */
    public static String convert(String str, EncodeConvertType type) {

        if (type != null) {
            return convert(str, type.fromEncode, type.toEncode);
        } else {
            return "";
        }
    }

}
