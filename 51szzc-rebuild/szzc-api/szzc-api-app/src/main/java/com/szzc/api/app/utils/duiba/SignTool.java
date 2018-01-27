package com.szzc.api.app.utils.duiba;

import com.jucaifu.common.configs.BusinessConfig;
import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.util.UrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

/**
 * MD5签名或签名验证工具类
 *
 * @author yangyy
 * @message Created by yangyy on 16/04/26
 */
public class SignTool {

    private static final Logger _logger = LoggerFactory.getLogger(SignTool.class);

    private static final String appSecret = BusinessConfig.DUIBA_MD5_SIGN_SECRET;

    public static boolean signVerify(HttpServletRequest request) throws UnsupportedEncodingException {
        return signVerify(appSecret, request);
    }

    public static boolean signVerify(String appSecret, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> data = new HashMap<>();
        for (String key : map.keySet()) {
            data.put(key, map.get(key)[0]);
        }
        return signVerify(appSecret, data);
    }

    public static boolean signVerify(String appSecret, Map<String, String> params) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        map.put("appSecret", appSecret);

        for (String key : params.keySet()) {
            if (!key.equals("sign")) {
                map.put(key, UrlHelper.decode(params.get(key)));
            }
        }

        String sign = sign(map);

        String requestSign = params.get("sign");

        _logger.info("请求参数签名结果为：" + requestSign + ",兑吧传来的签名结果为：" + sign);

        if (sign.equals(requestSign)) {
            return true;
        }
        return false;
    }

    private static String toHexValue(byte[] messageDigest) {
        if (messageDigest == null)
            return "";
        StringBuilder hexValue = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            int val = 0xFF & aMessageDigest;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * @param params
     * @return
     */
    public static String signRequestParam(Map<String, String> params) {
        if (params == null) {
            return StringPool.BLANK;
        }
        params.put("appSecret", appSecret);
        return sign(params);
    }

    /**
     * @param params
     * @return
     */
    public static String sign(Map<String, String> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String string = "";
        for (String s : keys) {
            string += params.get(s);
        }
        String sign = "";
        try {
            sign = toHexValue(encryptMD5(string.getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("md5 error");
        }
        return sign;
    }

    private static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String appKey = "key";
        String appSecret = "secret";

        Map<String, String> params = new HashMap<>();
        params.put("appKey", appKey);
        params.put("appSecret", appSecret);
        params.put("date", new Date().getTime() + "");

        String sign = sign(params);
        System.out.println("sign=" + sign);
        params.put("sign", sign);

        System.out.println(signVerify(appSecret, params));

    }
}
