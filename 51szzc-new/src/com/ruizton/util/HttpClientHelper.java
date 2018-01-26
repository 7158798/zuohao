package com.ruizton.util;

import com.ruizton.main.log.LOG;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
public class HttpClientHelper {

    private HttpClientHelper() {

    }

    /**
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param requestParams 请求参数
     * @return 应答字符串
     * @throws IOException
     */
    public static String post(String requestUrl, Map<String, String> requestParams) throws IOException {
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nameValuePairs.add(nameValuePair);
            }

            httpEntity = new UrlEncodedFormEntity(nameValuePairs, StringPool.UTF8);
        }

        HttpPost httpPost = new HttpPost(requestUrl);

        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }

        CloseableHttpClient httpClient = HttpClients.custom().build();

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = httpClient.execute(httpPost);

            HttpEntity responseHttpEntity = closeableHttpResponse.getEntity();

            return EntityUtils.toString(responseHttpEntity, StringPool.UTF8);
        } finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    /**
     * Send get request.
     *
     * @param uri   the uri
     * @param isLAN the is lAN
     * @return the string
     */
    public static String sendGetRequest(String uri, boolean isLAN) {
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
        return sendGetRequest(httpClient, uri, isLAN);
    }


    /**
     * Send get request.
     *
     * @param httpClient the http client
     * @param uri        the uri
     * @param isLAN      the is lAN
     * @return the string
     */
    public static String sendGetRequest(CloseableHttpClient httpClient, String uri, boolean isLAN) {

        String respStr = null;

        CloseableHttpResponse response = null;
        InputStream inStream = null;

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(HttpConnectionManager.getsRequestConfig(isLAN));

        try {
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream, "utf-8");
            }

        } catch (Exception ex) {

            LOG.e("", "sendGetRequest-ex", ex);

        } finally {

            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }

        return respStr;
    }



    private static boolean isMsWordByContentType(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase("Content-Type")) {
                if (!header.getValue().equalsIgnoreCase("application/json;charset=UTF-8")) {
                    return true;
                }
            }
        }
        return false;
    }

}
