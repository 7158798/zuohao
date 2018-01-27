package com.jucaifu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Hex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lx on 16-11-11.
 */
public class MacAddressHelper {

    private static String callCmd(String[] cmd) {
        String result = "";
        String line;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     *
     *
     * @param cmd
     *            第一个命令
     *
     * @param another
     *            第二个命令
     *
     * @return 第二个命令的执行结果
     *
     */

    private static String callCmd(String[] cmd,String[] another) {
        String result = "";
        String line;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader (is);
            while ((line = br.readLine ()) != null) {
                result += line;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     *
     *
     * @param ip
     *            目标ip,一般在局域网内
     *
     * @param sourceString
     *            命令处理的结果字符串
     *
     * @param macSeparator
     *            mac分隔符号
     *
     * @return mac地址，用上面的分隔符号表示
     *
     */

    private static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) {
        String result = "";
        String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(sourceString);
        while(matcher.find()){
            result = matcher.group(1);
            if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                // 如果有多个IP,只匹配本IP对应的Mac.
                break;
            }
        }
        return result;
    }

    /**
     *
     *
     *
     * @param ip
     *            目标ip
     *
     * @return Mac Address
     *
     *
     *
     */

    private static String getMacInWindows(final String ip){
        String result = "";
        String[] cmd = {"cmd","/c","ping " + ip};
        String[] another = {"cmd","/c","arp -a"};
        String cmdResult = callCmd(cmd,another);
        result = filterMacAddress(ip,cmdResult,"-");
        return result;
    }
    /**
     *
     * @param ip
     *            目标ip
     * @return Mac Address
     *
     */
    private static String getMacInLinux(final String ip){
        String result;
        String[] cmd = {"/bin/sh","-c","ping " +  ip + " -c 2 && arp -a" };
        String cmdResult = callCmd(cmd);
        result = filterMacAddress(ip,cmdResult,":");
        return result;
    }

    /**
     * 获取MAC地址
     *
     * @return 返回MAC地址
     */
    public static String getMacAddress(String ip){
        String macAddress ;
        macAddress = getMacInLinux(ip).trim();
        /*//macAddress = getMacInWindows(ip).trim();
        if(macAddress==null||"".equals(macAddress)){

        }*/
        if (StringUtils.isEmpty(macAddress)){
            return null;
        }
        return macAddress;
    }



    public static void main(String[] args) {
        /**/
        System.out.println("取消Tag开始： " + DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        String mac=getMacAddress("130.252.100.80");
        System.out.println("mac:"+mac);
        System.out.println("取消Tag开始： " + DateHelper.date2String(new Date(), DateHelper.DateFormatType.YearMonthDay_HourMinuteSecond));
        /*String str = "{\"body\":\"{\\\"page\\\":1,\\\"size\\\":20}\",\"header\":{\"appChannel\":\"AppStore\",\"appName\":\"jucaifu\",\"appVersion\":\"999.0.0\",\"deviceInfo\":{\"deviceType\":\"2\",\"name\":\"iPhone6s-postman\"},\"latitude\":\"\",\"longitude\":\"\",\"reqTimeStamp\":1478848154206,\"seqNo\":1,\"token\":\"\"},\"sign\":\"62f1a23a686ab5bf379b46a5e86ddd14\"}";
        System.out.println(str);
        System.out.println(Hex.encodeToString(str.getBytes()));*/
    }

}
