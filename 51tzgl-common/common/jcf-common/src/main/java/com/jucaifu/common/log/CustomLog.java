package com.jucaifu.common.log;

import com.jucaifu.common.util.IpHelper;
import com.jucaifu.common.util.JsonHelper;

/**
 * CustomLog
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/14.
 */
class CustomLog extends BaseLog implements TagFormatConstant {

    /**
     * Gets clazz.
     *
     * @param context the context
     * @return the clazz
     */
    private static Class getClazz(Object context) {

        Class clazz = CustomLog.class;

        if (context != null) {
            if (context instanceof Class) {
                clazz = (Class) context;
            } else {
                clazz = context.getClass();
            }
        }

        return clazz;
    }

    /**
     * D tag.
     *
     * @param context the context
     * @param tag     the tag
     */
//////////////////////////////
    //// Debug LOG by Tag
    //////////////////////////////
    public static void dTag(Object context, String tag) {

        d(getClazz(context), TAG_FORMAT, tag);
    }

    /**
     * D start.
     *
     * @param context the context
     * @param tag     the tag
     */
    public static void dStart(Object context, String tag) {
        d(getClazz(context), TAG_FORMAT_BEGIN, tag);
    }

    /**
     * D end.
     *
     * @param context the context
     * @param tag     the tag
     */
    public static void dEnd(Object context, String tag) {
        d(getClazz(context), TAG_FORMAT_END, tag);
    }

    /**
     * D ip tag.
     *
     * @param context the context
     * @param tag     the tag
     */
//////////////////////////////
    //// Debug LOG by Tag and ip
    //////////////////////////////
    public static void dIpTag(Object context, String tag) {

        String requestIp = IpHelper.getRequestIp();
        if (requestIp == null) {
            requestIp = "";
        }

        d(getClazz(context), TAG_FORMAT_IP, requestIp, tag);
    }

    /**
     * D ip begin.
     *
     * @param context the context
     * @param tag     the tag
     */
    public static void dIpBegin(Object context, String tag) {

        String requestIp = IpHelper.getRequestIp();
        if (requestIp == null) {
            requestIp = "";
        }

        d(getClazz(context), TAG_FORMAT_BEGIN, requestIp, tag);
    }

    /**
     * D ip end.
     *
     * @param context the context
     * @param tag     the tag
     */
    public static void dIpEnd(Object context, String tag) {

        String requestIp = IpHelper.getRequestIp();
        if (requestIp == null) {
            requestIp = "";
        }
        d(getClazz(context), TAG_FORMAT_IP_END, requestIp, tag);
    }

    /**
     * D void.
     *
     * @param context the context
     * @param object  the object
     */
//////////////////////////////
    //// LOG Object json string
    //////////////////////////////
    public static void d(Object context, Object object) {
        String jsonStr = JsonHelper.obj2JsonStrWithDateFormat(object);
        d(getClazz(context), jsonStr);
    }

    /**
     * I void.
     *
     * @param context the context
     * @param object  the object
     */
    public static void i(Object context, Object object) {
        String jsonStr = JsonHelper.obj2JsonStrWithDateFormat(object);
        i(getClazz(context), jsonStr);
    }

    /**
     * W void.
     *
     * @param context the context
     * @param object  the object
     */
    public static void w(Object context, Object object) {
        String jsonStr = JsonHelper.obj2JsonStrWithDateFormat(object);
        w(getClazz(context), jsonStr);
    }

    /**
     * E void.
     *
     * @param context the context
     * @param object  the object
     * @param e       the e
     */
    public static void e(Object context, Object object, Throwable e) {
        String jsonStr = JsonHelper.obj2JsonStrWithDateFormat(object);
        e(getClazz(context), jsonStr, e);
    }

}
