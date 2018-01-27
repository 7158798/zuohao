package com.jucaifu.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/1.
 */
public class StringHelper {

    private static final int defualtNum = 2;

    private StringHelper() {
    }

    /**
     * Merge mutable args.
     *
     * @param splitLine the split line
     * @param args      the args
     * @return the string
     */
    public static String mergeMutableArgs(String splitLine, Object... args) {
        if (splitLine == null) {
            splitLine = "";
        }

        StringBuffer sb = new StringBuffer();
        if (args != null && args.length > 0) {
            Object item;
            for (int i = 0; i < args.length; i++) {
                item = args[i] == null ? "" : args[i];
                if (i == 0) {
                    sb.append("" + item);
                } else {
                    sb.append(splitLine + item);
                }
            }
        }

        return sb.toString();
    }


    /*****************************************
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true, 否则返回false
     * @author : scofieldandroid@gmail.com
     * @Title : isChinese
     * @returnType : boolean
     * @Description: 判断输入的字符串是否为纯汉字
     *****************************************/
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+");
        return pattern.matcher(str).matches();
    }

    /*****************************************
     * @param str
     * @return
     * @author : scofieldandroid@gmail.com
     * @Title : isNumeric
     * @returnType : boolean
     * @Description: 判断是否为纯数字
     *****************************************/
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /*****************************************
     * @param str
     * @return
     * @author : scofieldandroid@gmail.com
     * @Title : isInteger
     * @returnType : boolean
     * @Description: 判断是否为整数
     *****************************************/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /*****************************************
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     * @author : scofieldandroid@gmail.com
     * @Title : isDouble
     * @returnType : boolean
     * @Description: 判断是否为浮点数，包括double和float
     * 点击需要 \\.
     *****************************************/
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+\\.[\\d]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 格式化小数位-默认2位
     * @param str
     * @return
     */
    public static String numberDecimalDigitFormat(String str){
        if(StringUtils.isBlank(str)){
            return "0";
        }
        return numberDecimalDigitFormat(str, defualtNum);
    }

    /**
     * 格式化小数位-传入的位数
     * @param str
     * @param num
     * @return
     */
    public static String numberDecimalDigitFormat(String str, int num){
        if(StringUtils.isBlank(str)){
            return "0";
        }else{
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(false);
            nf.setMaximumFractionDigits(num);
            nf.setMinimumFractionDigits(num);
            nf.setRoundingMode(RoundingMode.DOWN);
            return nf.format(Double.parseDouble(str));
        }
    }

    /**
     * 格式化小数位-默认2位
     * @param d
     * @return
     */
    public static String numberDecimalDigitFormat(double d){
        return numberDecimalDigitFormat(d, defualtNum);
    }

    /**
     * 格式化小数位-传入的位数
     * @param d
     * @param num
     * @return
     */
    public static String numberDecimalDigitFormat(double d, int num){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(num);
        nf.setMinimumFractionDigits(num);
        nf.setRoundingMode(RoundingMode.DOWN);
        return nf.format(d);
    }

    /**
     * 百分数-字符串
     * @param str
     * @return
     */
    public static String percent(String str, int num){
        if(StringUtils.isNotBlank(str)){
            return percent(Double.parseDouble(str), num);
        }else{
            return "0";
        }
    }

    /**
     * 百分数-double
     * @param d
     * @return
     */
    public static String percent(double d, int num){
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(num);
        nf.setMinimumFractionDigits(num);
        return nf.format(d);
    }

    public static String getUUID(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        return replace;
    }

}
