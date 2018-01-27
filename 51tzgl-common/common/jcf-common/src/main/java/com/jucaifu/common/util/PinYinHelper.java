package com.jucaifu.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * PinYinHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/2.
 */
public class PinYinHelper {

    private PinYinHelper() {
    }

    private final static HanyuPinyinOutputFormat sPinyinOutputFormat = new HanyuPinyinOutputFormat();

    static {
        sPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        sPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        sPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * Str 2 pinyin.
     *
     * @param str  the str
     * @param fill the fill
     * @return the string
     */
    public static String str2Pinyin(String str, String fill) {
        try {
            StringBuffer sb = new StringBuffer();
            if (fill == null) fill = "";
            boolean isCn = true;
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (i > 0 && isCn) {
                    sb.append(fill);
                }
                if (c == ' ') {
                    sb.append(fill);
                }
                //1、判断c是不是中文
                if (c >= '\u4e00' && c <= '\u9fa5') {
                    isCn = true;
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, sPinyinOutputFormat)[0]);
                } else {
                    //不是中文
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                        sb.append(c);
                    }
                    isCn = false;
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Str first 2 pinyin.
     *
     * @param str the str
     * @return the string
     */
    public static String strFirst2Pinyin(String str) {
        try {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);

                //1、判断c是不是中文
                if (c >= '\u4e00' && c <= '\u9fa5') {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(c, sPinyinOutputFormat)[0].charAt(0));
                } else {

                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return null;
    }
}
