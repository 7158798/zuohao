package com.jucaifu.common.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangyy on 15-10-22.
 */
public class Validator {

    /**
     * 判断字符串是否全数字
     *
     * @param data
     * @return
     */
    public static boolean isNumber(String data) {

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(data);
        return isNum.matches();
    }

    /**
     * 判断字符串是否可以转换为Integer
     *
     * @author Angi Wang
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否可以转换为Long
     *
     * @author Angi Wang
     *
     * @param str
     * @return
     */
    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断金额格式（两位小数）
     *
     */
    public static boolean isMoney(String money) {

        String[] tmpMoney = money.split("[.]");
        String tmp = "";
        for (int i = 0; i < tmpMoney.length; i++) {
            tmp += tmpMoney[i];
        }

        if (!isNumber(tmp)) {
            return false;
        }
        if (!money.contains(".")) {
            return false;
        } else {
            for (int i = 0; i < tmpMoney.length; i++) {
                if (i == 1) {
                    tmp = "";
                    tmp += tmpMoney[i];
                }

            }
            if (tmp.length() != 2) {
                return false;
            }
        }
        return true;
    }
}
