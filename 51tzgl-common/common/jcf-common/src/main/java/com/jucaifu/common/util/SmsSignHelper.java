package com.jucaifu.common.util;

import com.jucaifu.common.log.LOG;
import com.jucaifu.common.security.Base64Helper;
import org.apache.commons.lang3.StringUtils;

/**
 * 发送短信时签名
 * Created by liuxun on 16-9-19.
 */
public class SmsSignHelper {

    private final static String REPLACE_KEY = "=";

    /**
     * 验证签名
     * @param phoneNumber      手机号码
     * @param sign             手机号码签名
     * @return
     */
    public static boolean validateSign(String phoneNumber,String sign){
        boolean flag = false;
        // 两次Base64加密
        byte[] bytes = Base64Helper.encode(Base64Helper.encode(phoneNumber));
        String result = new String(bytes);
        // 移除所有的等号
        result = result.replaceAll(REPLACE_KEY,"");
        StringBuffer resultBuffer = new StringBuffer();
        // 获取非前四位
        resultBuffer.append(result.substring(4));
        // 获取请四位
        resultBuffer.append(result.substring(0,4));
        result = resultBuffer.toString();
        LOG.d(SmsSignHelper.class,"签名为: " + result);
        if (StringUtils.equals(result,sign)){
            // 签名验证通过
            flag = true;
        }
        return flag;
    }
}
