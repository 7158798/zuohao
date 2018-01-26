package com.ruizton.main.log;

import com.ruizton.util.JsonHelper;

/**
 * Created by a123 on 17-2-8.
 */
public class LOG extends BaseLog implements TagFormatConstant {

    /**
     * Gets clazz.
     *
     * @param context the context
     * @return the clazz
     */
    private static Class getClazz(Object context) {

        Class clazz = LOG.class;

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

