/**
 * Creation Date:2014-1-20下午12:53:39
 * Copyright (c) 2014, 上海佐昊网络科技有限公司 All Rights Reserved.
 */
package com.jucaifu.common.network;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * 请求对象
 *
 * @author zhaiyz
 */
public class RequestObject {

    /** 主机地址 */
    private String hostUrl;

    /** 方法地址 */
    private String methodUrl;

    /** 参数 */
    private List<NameValuePair> values;

    /**
     * 取得请求地址
     *
     * @return hostUrl + methodUrl
     */
    public String getRequestUrl() {
        return hostUrl + methodUrl;
    }

    /**
     * @return the hostUrl
     */
    public String getHostUrl() {
        return hostUrl;
    }

    /**
     * @param hostUrl
     *            the hostUrl to set
     */
    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    /**
     * @return the methodUrl
     */
    public String getMethodUrl() {
        return methodUrl;
    }

    /**
     * @param methodUrl
     *            the methodUrl to set
     */
    public void setMethodUrl(String methodUrl) {
        this.methodUrl = methodUrl;
    }

    /**
     * @return the values
     */
    public List<NameValuePair> getValues() {
        return values;
    }

    /**
     * @param values
     *            the values to set
     */
    public void setValues(List<NameValuePair> values) {
        this.values = values;
    }
}
