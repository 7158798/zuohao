package com.ruizton.util;

import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Faddress;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mibook3 on 17-4-25.
 */
public class TaoBaoIpHelper {

    public static  ConcurrentHashMap<String,String> pool=new  ConcurrentHashMap<String,String>();
    public static String ipSearchUrl="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    public static String url="http://ip.taobao.com/service/getIpInfo.php?ip=";

    static {
        pool.put("127.0.0.1","本地");
        pool.put("0:0:0:0:0:0:0:1", "本地");
    }
    public static String getAddress(String ip){

           if(pool.containsKey(ip)){
               return pool.get(ip);
           }
        String  ipAddress="";

     // String jsonStr= HttpClientHelper.sendGetRequest(url+ip,false);
      //  System.out.println(decodeUnicode(jsonStr));

        String remoteIpInfo=HttpClientHelper.sendGetRequest(ipSearchUrl+ip,false);

        if(StringUtils.isBlank(remoteIpInfo)) {
            LOG.e(TaoBaoIpHelper.class, "根据ip查询城市为空，ip为：" + ip);
            ipAddress = "未知";
            return ipAddress;
        }

        remoteIpInfo=decodeUnicode(remoteIpInfo);
        if(remoteIpInfo.contains("{")) {  //排除-2的情況
            JSONObject jsonObject = JSONObject.fromObject(remoteIpInfo);
            if (jsonObject.getInt("ret") == 1) {
                Faddress address = (Faddress) JSONObject.toBean(jsonObject, Faddress.class);
                ipAddress = address.getCountry() + address.getProvince() + address.getCity();
                System.out.println(ipAddress);
                //存入新ip
                pool.put(ip, ipAddress);
            }
        }
        return ipAddress;

    }





    public static void main(String[] args) {
        getAddress("183.129.210.50");
   /*String  r=getAddress("127.0.0.1");
   System.out.println(r);*/
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
