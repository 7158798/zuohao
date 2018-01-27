package com.jucaifu.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

import com.jucaifu.common.constants.StringPool;
import com.jucaifu.common.enums.EnumHttpMethod;
import com.jucaifu.common.log.LOG;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HttpServletHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/14.
 */
public class HttpServletHelper {
    private HttpServletHelper() {
    }

    private static HttpServletHelper sInstance = new HttpServletHelper();

    /**
     * Is option request.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isOptionRequest(HttpServletRequest request) {

        boolean isOptionRequest = false;
        if (request != null) {
            String method = request.getMethod();
            isOptionRequest = EnumHttpMethod.OPTIONS.getValue().equals(method);
        }

        return isOptionRequest;
    }

    /**
     * Gets context full path.
     *
     * @return the context full path
     */
    public static String getContextFullPath() {
        String contextFullPath = null;
        HttpServletRequest request = getRequest();

        if (request != null) {
            contextFullPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        }

//        LOG.d(sInstance, contextFullPath);

        return contextFullPath;
    }

    /**
     * Gets context file full path.
     *
     * @param filePath the file path
     * @return the context file full path
     */
    public static String getContextFileFullPath(String filePath) {
        String contextFullPath = getContextFullPath();
        String fileFullPath = null;
        if (contextFullPath != null && filePath != null) {
            fileFullPath = contextFullPath + filePath;
        }
        return fileFullPath;
    }

    /**
     * Log all request headers.
     *
     * @param request the request
     */
    public static void logAllRequestHeaders(HttpServletRequest request) {

        if (request != null) {
            LOG.d(sInstance, "requestURI :" + request.getRequestURI());
            LOG.d(sInstance, "requestMethod :" + request.getMethod());
            LOG.d(sInstance, "requestQueryString :" + request.getQueryString());
            LOG.d(sInstance, "requestIp :" + getRequestIp(request));

            Enumeration enu = request.getHeaderNames();//取得全部头信息

            while (enu.hasMoreElements()) {//以此取出头信息

                String headerName = (String) enu.nextElement();
                String headerValue = request.getHeader(headerName);//取出头信息内容

                LOG.d(sInstance, headerName + ":" + headerValue);
            }

            Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    LOG.d(sInstance, "Cookie-" + cookie.getName() + ":" + cookie.getValue());
                }
            }
        }
    }


    /**
     * Add CORS response header.
     *
     * @param response the response
     */
    public static void addCORSResponseHeader(HttpServletResponse response) {

        if (response != null) {
            LOG.dTag(sInstance, "addCORSResponseHeader");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,*");
            response.setHeader("Allow", "GET, POST, PUT, OPTIONS,DELETE");
//            Access-Control-Allow-Methods: PUT, DELETE
//            response.setHeader("Allow", "POST, GET,PUT, OPTIONS, DELETE");
//            GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH
        }

    }

    /**
     * Add base response header.
     *
     * @param response the response
     */
    @Deprecated
    public static void addBaseResponseHeader(HttpServletResponse response) {

        if (response != null) {
            LOG.dTag(sInstance, "addBaseResponseHeader");
            HttpServletHelper.addCORSResponseHeader(response);
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE");
//            response.setHeader("Access-Control-Allow-Methods", "POST");
//            response.setHeader("Access-Control-Allow-Headers", "*");
//            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        }
    }


    /**
     * Gets request.
     *
     * @return the request
     */
    public static HttpServletRequest getRequest() {

        HttpServletRequest request = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            request = attributes.getRequest();
        }

        return request;
    }

    /**
     * Get session.
     *
     * @param httpServletRequest the http servlet request
     * @return the http session
     */
    public static HttpSession getSession(HttpServletRequest httpServletRequest) {

        HttpSession session = null;

        if (httpServletRequest != null) {
            session = httpServletRequest.getSession();
        }

        return session;
    }

    /**
     * Gets session attribute.
     *
     * @param httpServletRequest the http servlet request
     * @param var1               the var 1
     * @return the session attribute
     */
    public static <T> T getSessionAttribute(HttpServletRequest httpServletRequest, String var1) {
        T result = null;

        HttpSession session = getSession(httpServletRequest);

        if (session != null && var1 != null) {
            result = (T) session.getAttribute(var1);
        }

        return result;
    }

    /**
     * Get session attribute.
     *
     * @param var1 the var 1
     * @return the t
     */
    public static <T> T getSessionAttribute(String var1) {

        T result = null;

        HttpSession session = getSession();

        if (session != null && var1 != null) {
            result = (T) session.getAttribute(var1);
        }

        return result;
    }

    /**
     * Get session.
     *
     * @return the http session
     */
    public static HttpSession getSession() {
        HttpSession session = null;

        HttpServletRequest request = getRequest();
        if (request != null) {
            session = getSession(request);
        }
        return session;
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
            requestIp = request.getHeader("x-forwarded-for");
            if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
                requestIp = request.getHeader("Proxy-Client-IP");
                if (requestIp == null) {
                    requestIp = request.getHeader("WL-Proxy-Client-IP");
                }
                if (requestIp == null) {
                    requestIp = request.getRemoteAddr();
                }
            }
        }

        if (requestIp == null) {
            requestIp = "";
        }

        return requestIp;
    }

    /**
     * Gets request ip.
     *
     * @return the request ip
     */
    public static String getRequestIp() {
        HttpServletRequest request = getRequest();
        return getRequestIp(request);
    }

    /**
     * Log request info.
     *
     * @param <T> the type parameter
     * @param obj the obj
     */
    public static <T> void logRequestInfo(T obj) {
        LOG.dTag(sInstance, "请求数据如下");
        LOG.d(sInstance, obj);
    }

    /**
     * Log response info.
     *
     * @param <T> the type parameter
     * @param obj the obj
     */
    public static <T> void logResponseInfo(T obj) {
        LOG.dTag(sInstance, "响应数据如下");
        LOG.d(sInstance, obj);
    }

    /**
     * Get request header message
     *
     * @param var
     * @return
     */
    public static String getRequestHeaderMessage(String var){
        String headerMessage = StringPool.BLANK;
        HttpServletRequest request = getRequest();
        if(request != null){
            headerMessage = request.getHeader(var);
        }
        return headerMessage;
    }

}
