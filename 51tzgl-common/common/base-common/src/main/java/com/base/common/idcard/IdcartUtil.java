package com.base.common.idcard;

import com.jucaifu.common.network.HttpClientHelper;
import com.jucaifu.common.property.PropertiesUtils;
import com.jucaifu.common.util.JsonHelper;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenggq on 17-4-25.
 */
public class IdcartUtil {

    //配置您申请的KEY
    public static final String APPKEY = PropertiesUtils.getProperty("idcard.key");

    public static final String URL =  PropertiesUtils.getProperty("idcard.url");

    public static boolean isRealPerson(String realName,String number){
        boolean flag = false;
        try {
            String params = "idcard="+number+"&realname="+realName+"&key="+APPKEY;
            JSONObject retJson = JSONObject.fromObject(HttpClientHelper.sendGetRequest(URL,params,false));
            int res = retJson.getJSONObject("result").getInt("res");
            int error_code = retJson.getInt("error_code");
            if(error_code==0 &&res==1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 获取出生年月日
     *
     * @param cerNo
     * @return
     */
    public static String getBirthday(String cerNo) {
        String birthday = null;
        if (cerNo.length() == 15) {
            String year = cerNo.substring(6, 8);
            String month = cerNo.substring(8, 10);
            String day = cerNo.substring(10, 12);
            birthday = "19" + year + "-" + month + "-" + day;
        }
        if (cerNo.length() == 18) {
            String year = cerNo.substring(6, 10);
            String month = cerNo.substring(10, 12);
            String day = cerNo.substring(12, 14);
            birthday = year + "-" + month + "-" + day;
        }
        return birthday;
    }

    /**
     * 获取性别
     *
     * @param cerNo
     * @return
     */
    public static Integer getGender(String cerNo) {
        Integer gender = null;
        if (cerNo.length() == 15) {
            String flag = cerNo.substring(13, 14);
            int tag = Integer.parseInt(flag);
            if (tag % 2 == 0) {
                gender = 0;
            } else {
                gender = 1;
            }
        }
        if (cerNo.length() == 18) {
            String flag = cerNo.substring(16, 17);
            int tag = Integer.parseInt(flag);
            if (tag % 2 == 0) {
                gender = 0;
            } else {
                gender = 1;
            }
        }
        return gender;
    }


}
