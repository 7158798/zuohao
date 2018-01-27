package com.jucaifu.common.network;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * HttpConnectionManager
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/10/20.
 */
public class HttpConnectionManager {

    private static PoolingHttpClientConnectionManager sPoolingConnectionManager = null;

    private static RequestConfig sRequestConfig = null;
    private static RequestConfig sLANRequestConfig = null;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;

    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;

    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 局域网－最大等待时间
     */
    public final static int LAN_WAIT_TIMEOUT = 30000;


    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 10000;
    /**
     * 局域网－连接超时时间
     */
    public final static int LAN_CONNECT_TIMEOUT = 10000;

    /**
     * 读取超时时间
     */
    public final static int READ_TIMEOUT = 10000;
    /**
     * 局域网－读取超时时间
     */
    public final static int LAN_READ_TIMEOUT = 10000;

    static {
        sPoolingConnectionManager = new PoolingHttpClientConnectionManager();

        // Increase max total connection to 200
        sPoolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        // Increase default max connection per route to 20
        sPoolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        // Increase max connections for localhost:80 to 50
//        HttpHost localhost = new HttpHost("locahost", 80);
//        sPoolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), 50);

        sRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(WAIT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(READ_TIMEOUT)
                .build();

        sLANRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(LAN_WAIT_TIMEOUT)
                .setConnectTimeout(LAN_CONNECT_TIMEOUT)
                .setSocketTimeout(LAN_READ_TIMEOUT)
                .build();

    }

    /**
     * Gets http client.
     *
     * @return the http client
     */
    public static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(sPoolingConnectionManager)
                .build();

        return httpClient;
    }

    /**
     * Gets request config.
     *
     * @param isLAN the is lAN
     * @return the request config
     */
    public static RequestConfig getsRequestConfig(boolean isLAN) {

        if (isLAN) {
            return sLANRequestConfig;
        } else {
            return sRequestConfig;
        }
    }
}
