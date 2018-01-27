package com.jucaifu.common.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.network.HttpClientHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * IpHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/10.
 */
public class IpHelper {

    public static ConcurrentHashMap<String,String> pool=new  ConcurrentHashMap<String,String>();

    private static String ipSearchUrl="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    static {
        pool.put("127.0.0.1","本地");
        pool.put("130.252.100.116","测试");
    }

    private IpHelper() {
    }

    /**
     * Gets local ip.
     *
     * @return the local ip
     */
    public static String getLocalIp() {

        String ip = null;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }


    /**
     * Gets request ip.
     *
     * @param request the request
     * @return the request ip
     */
    public static String getRequestIp(HttpServletRequest request) {

        String requestIp = null;
        if (request != null) {

            if (request.getHeader("x-forwarded-for") == null) {
                requestIp = request.getRemoteAddr();
            } else {
                requestIp = request.getHeader("x-forwarded-for");
            }

        } else {
            LOG.i(IpHelper.class, "request==null");
        }

        return requestIp;
    }


    /**
     * Gets request ip.
     *
     * @return the request ip
     */
    public static String getRequestIp() {

        return getRequestIp(HttpServletHelper.getRequest());
    }


    public static String getMacAddress(String ipAddress){
        String macAddress = null;
        try {
            Process p = Runtime.getRuntime().exec("arp -n");
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            p.waitFor();
            boolean flag = true;
            String ipStr = "(" + ipAddress + ")";
            while(flag) {
                String str = input.readLine();
                if (str != null) {
                    if (str.indexOf(ipStr) > 1) {
                        int temp = str.indexOf("at");
                        macAddress = str.substring(
                                temp + 3, temp + 20);
                        break;
                    }
                } else
                    flag = false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }

    /**
     * 通过ip获取地址
     * @param ip
     * @return
     */
    public static String getAddress(String ip){
        if(StringUtils.isBlank(ip)){
            return "";
        }
        if(pool.containsKey(ip)){
            return pool.get(ip);
        }
        String  ipAddress="未知";
        try{
            String remoteIpInfo= HttpClientHelper.sendGetRequest(ipSearchUrl+ip,false);

            System.out.println( remoteIpInfo);
            remoteIpInfo=decodeUnicode(remoteIpInfo);
            JSONObject jsonObject = JsonHelper.jsonStr2JsonObj(remoteIpInfo);
            if(jsonObject.getLong("ret") == 1){
                ipAddress = jsonObject.getString("country")+jsonObject.getString("province")+jsonObject.getString("city");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ipAddress;
    }

    /**
     * unicode 转换成 中文
     *
     * @author fanhui 2007-3-15
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }





}
