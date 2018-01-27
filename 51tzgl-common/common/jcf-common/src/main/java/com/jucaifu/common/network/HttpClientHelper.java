package com.jucaifu.common.network;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.log.LOG;
import com.jucaifu.common.util.ValueHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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
     * Send get request.
     *
     * @param apiUrl      the api url
     * @param queryString the query string
     * @param isLAN       the is lAN
     * @return the string
     */
    public static String sendGetRequest(String apiUrl, String queryString, boolean isLAN) {

        StringBuilder sb = new StringBuilder(apiUrl);

        if (!ValueHelper.checkStringIsEmpty(queryString)) {
            sb.append("?");
            sb.append(queryString);
        }

        String uri = sb.toString();
        LOG.d(HttpClientHelper.class, uri);

        return sendGetRequest(uri, isLAN);
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
     * send get for error
     * @param uri
     * @param isLAN
     * @return
     */
    public static String sendGetErrorRequest(String uri,Boolean isLAN){
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
        return sendGetRequestForErrorCode(httpClient, uri, isLAN);
    }

    /**
     * Send post form request.
     *
     * @param reqObj the req obj
     * @param isLAN  the is lAN
     * @return the string
     */
    public static String sendPostFormRequest(RequestObject reqObj, boolean isLAN) {
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
        return sendPostFormRequest(httpClient, reqObj, isLAN);
    }

    /**
     * Send post form request.
     *
     * @param httpClient the http client
     * @param reqObj     the req obj
     * @param isLAN      the is lAN
     * @return the string
     */
    public static String sendPostFormRequest(CloseableHttpClient httpClient, RequestObject reqObj, boolean isLAN) {

        String respStr = null;

        CloseableHttpResponse response = null;
        InputStream inStream = null;

        HttpPost httpPost = new HttpPost(reqObj.getRequestUrl());
        httpPost.setConfig(HttpConnectionManager.getsRequestConfig(isLAN));

        try {

            List<NameValuePair> nameValuePairList = reqObj.getValues();
            if (nameValuePairList != null) {
                UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
                httpPost.setEntity(urlEntity);
            }

            response = httpClient.execute(httpPost);



            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return respStr;
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

            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream);
            }

        } catch (Exception ex) {

            LOG.e("","sendGetRequest-ex",ex);

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


    /**
     * Send get request.
     *
     * @param httpClient the http client
     * @param uri        the uri
     * @param isLAN      the is lAN
     * @return the string
     */
    public static String sendGetRequestForErrorCode(CloseableHttpClient httpClient, String uri, boolean isLAN) {

        String respStr = null;

        CloseableHttpResponse response = null;
        InputStream inStream = null;

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(HttpConnectionManager.getsRequestConfig(isLAN));
        int code;
        try {
            response = httpClient.execute(httpGet);

            code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (entity != null && code == 200) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream);
            }else{
                respStr = "request is error";
            }

        } catch (Exception ex) {

            LOG.e("","sendGetRequest-ex",ex);

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

    /**
     * Send get request.
     *
     * @param uri        the uri
     * @param isLAN      the is lAN
     * @return the string
     */
    public static String sendGetRequestForAgent(String uri, String agent,boolean isLAN) {
        CloseableHttpClient httpClient = HttpConnectionManager.getHttpClient();
        String respStr = null;

        CloseableHttpResponse response = null;
        InputStream inStream = null;

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(HttpConnectionManager.getsRequestConfig(isLAN));
        if(StringUtils.isNotBlank(agent)){
            httpGet.setHeader("User-Agent",agent);
        }

        try {
            response = httpClient.execute(httpGet);

            int code = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream);
            }

        } catch (Exception ex) {

            LOG.e("","sendGetRequest-ex",ex);

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


    /**
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param requestParams 请求参数
     * @return 应答字符串
     * @throws IOException
     */
    public static String postForAgent(String requestUrl, String agent,Map<String, String> requestParams) throws IOException {
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
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
        if(StringUtils.isNotBlank(agent)){
            httpPost.setHeader("User-Agent",agent);
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
            List<NameValuePair> nameValuePairs = new ArrayList<>();
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
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param xmlStr        请求参数（xml数据类型）
     * @param socketFactory 证书
     * @return 应答字符串
     * @throws IOException
     */
    public static String post(String requestUrl, String xmlStr,SSLConnectionSocketFactory socketFactory) throws IOException {
        LOG.e(HttpClientHelper.class,"请求参数" + xmlStr);
        StringEntity httpEntity = new StringEntity(xmlStr,StringPool.UTF8);

        HttpPost httpPost = new HttpPost(requestUrl);

        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

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
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param requestParams 请求参数
     * @param userAgent     userAgent
     * @return 应答字符串
     * @throws IOException
     */
    public static String post(String requestUrl, Map<String, String> requestParams,String userAgent) throws IOException {
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
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
        CloseableHttpClient httpClient;
        if (StringUtils.isNotEmpty(userAgent)){
            // 用于百度推送
            httpClient = HttpClients.custom().setUserAgent(userAgent).build();
        } else {
            httpClient = HttpClients.custom().build();
        }

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
     * 获取文件流
     *
     * @param requestUrl    请求地址
     * @param requestParams 请求参数
     * @return 文件流或null
     * @throws IOException
     */
    public static InputStream postForFiles(String requestUrl, Map<String, String> requestParams) throws IOException {
        LOG.i(HttpClientHelper.class, "进入postForFiles");
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nameValuePairs.add(nameValuePair);
            }

            httpEntity = new UrlEncodedFormEntity(nameValuePairs, StringPool.UTF8);
        }

        LOG.i(HttpClientHelper.class, "参数封装结束");
        HttpPost httpPost = new HttpPost(requestUrl);

        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }

        CloseableHttpClient httpClient = HttpClients.custom().build();

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            LOG.i(HttpClientHelper.class, "调簿记系统开始");
            closeableHttpResponse = httpClient.execute(httpPost);
            LOG.i(HttpClientHelper.class, "调簿记系统结束");
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                LOG.i(HttpClientHelper.class, "statuscode = 200，请求成功" );
                if (isMsWordByContentType(closeableHttpResponse)) {
                    LOG.i(HttpClientHelper.class, "请求成功，返回content");
                    return closeableHttpResponse.getEntity().getContent();
                } else {
                    LOG.i(HttpClientHelper.class, "抛出异常，请求header错误");
                    throw new RuntimeException(EntityUtils.toString(closeableHttpResponse.getEntity(), StringPool.UTF8));
                }
            }
        } finally {
//            if (closeableHttpResponse != null) {
//                closeableHttpResponse.close();
//            }
//            if (httpClient != null) {
//                httpClient.close();
//            }
        }

        LOG.i(HttpClientHelper.class, "进入postForFiles，  方法执行完毕");
        return null;
    }

    private static boolean isMsWordByContentType(HttpResponse response) {
        LOG.i(HttpClientHelper.class, "进入isMsWordByContentType方法");
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            LOG.i(HttpClientHelper.class, "循环header.name = " + header.getName());
            if (header.getName().equalsIgnoreCase("Content-Type")) {
                if (!header.getValue().equalsIgnoreCase("application/json;charset=UTF-8")) {
                    LOG.i(HttpClientHelper.class, "进入isMsWordByContentType方法，返回true");
                    return true;
                }
            }
        }
        LOG.i(HttpClientHelper.class, "进入isMsWordByContentType方法，返回false");
        return false;
    }



    public static Map<String, String> objectToMap(Object obj) throws Exception {
        if(obj == null)
            return null;

        Map<String, String> map = new HashMap<String, String>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            if(value != null){
                map.put(key, value.toString());
            }

        }

        return map;
    }


    /**
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param paramsJson 请求参数
     * @return 应答字符串
     * @throws IOException
     */
    public static String postJsonParams(String requestUrl, String paramsJson) throws IOException {

        StringEntity s = new StringEntity(paramsJson, "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));


        HttpPost httpPost = new HttpPost(requestUrl);
        httpPost.setEntity(s);
        httpPost.setHeader("Content-Type", "application/json");


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








}
