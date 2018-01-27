package com.jucaifu.common.log;

import com.jucaifu.common.util.cache.UnitCacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseLog
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/11/14.
 */
class BaseLog {

    private static final UnitCacheHelper sUnitCacheHelper = new UnitCacheHelper();

    private static final Class DEFAULT_LOG_CLAZZ = BaseLog.class;

    protected static Logger getLogger(Class<?> clazz) {
        if (clazz == null) {
            clazz = DEFAULT_LOG_CLAZZ;
        }

        Logger logger = sUnitCacheHelper.quickGet(clazz.getName());

        if (logger == null) {
            logger = LoggerFactory.getLogger(clazz);
            sUnitCacheHelper.quickPut(clazz.getName(), logger);
        }

        return logger;
    }

    //////////////////////////////
    //// Enabled
    //////////////////////////////
    public static boolean isDebugEnabled(Class<?> clazz) {
        return getLogger(clazz).isDebugEnabled();
    }

    public static boolean isInfoEnabled(Class<?> clazz) {
        return getLogger(clazz).isInfoEnabled();
    }

    public static boolean isWarnEnabled(Class<?> clazz) {
        return getLogger(clazz).isWarnEnabled();
    }

    public static boolean isErrorEnabled(Class<?> clazz) {
        return getLogger(clazz).isErrorEnabled();
    }


    //////////////////////////////
    //// Level:Debug
    //////////////////////////////
    public static void d(Class<?> clazz, String msg) {
        if (isDebugEnabled(clazz)) {
            getLogger(clazz).debug(msg);
        }
    }

    public static void d(Class<?> clazz, String format, Object arg) {
        if (isDebugEnabled(clazz)) {
            getLogger(clazz).debug(format, arg);
        }
    }

    public static void d(Class<?> clazz, String format, Object arg1, Object arg2) {
        if (isDebugEnabled(clazz)) {
            getLogger(clazz).debug(format, arg1, arg2);
        }
    }

    public static void d(Class<?> clazz, String format, Object... arguments) {
        if (isDebugEnabled(clazz)) {
            getLogger(clazz).debug(format, arguments);
        }
    }

    public static void d(Class<?> clazz, String msg, Throwable t) {
        if (isDebugEnabled(clazz)) {
            getLogger(clazz).debug(msg, t);
        }
    }

    //////////////////////////////
    //// Level:Info
    //////////////////////////////
    public static void i(Class<?> clazz, String msg) {
        if (isInfoEnabled(clazz)) {
            getLogger(clazz).info(msg);
        }
    }

    public static void i(Class<?> clazz, String format, Object arg) {
        if (isInfoEnabled(clazz)) {
            getLogger(clazz).info(format, arg);
        }
    }

    public static void i(Class<?> clazz, String format, Object arg1, Object arg2) {
        if (isInfoEnabled(clazz)) {
            getLogger(clazz).info(format, arg1, arg2);
        }
    }

    public static void i(Class<?> clazz, String format, Object... arguments) {
        if (isInfoEnabled(clazz)) {
            getLogger(clazz).info(format, arguments);
        }
    }

    public static void i(Class<?> clazz, String msg, Throwable t) {
        if (isInfoEnabled(clazz)) {
            getLogger(clazz).info(msg, t);
        }
    }


    //////////////////////////////
    //// Level:warn
    //////////////////////////////
    public static void w(Class<?> clazz, String msg) {
        if (isWarnEnabled(clazz)) {
            getLogger(clazz).warn(msg);
        }
    }

    public static void w(Class<?> clazz, String format, Object arg) {
        if (isWarnEnabled(clazz)) {
            getLogger(clazz).warn(format, arg);
        }
    }

    public static void w(Class<?> clazz, String format, Object arg1, Object arg2) {
        if (isWarnEnabled(clazz)) {
            getLogger(clazz).warn(format, arg1, arg2);
        }
    }

    public static void w(Class<?> clazz, String format, Object... arguments) {
        if (isWarnEnabled(clazz)) {
            getLogger(clazz).warn(format, arguments);
        }
    }

    public static void w(Class<?> clazz, String msg, Throwable t) {
        if (isWarnEnabled(clazz)) {
            getLogger(clazz).warn(msg, t);
        }
    }


    //////////////////////////////
    //// Level:Error
    //////////////////////////////
    public static void e(Class<?> clazz, String msg) {
        if (isErrorEnabled(clazz)) {
            getLogger(clazz).error(msg);
        }
    }

    public static void e(Class<?> clazz, String format, Object arg) {
        if (isErrorEnabled(clazz)) {
            getLogger(clazz).error(format, arg);
        }
    }

    public static void e(Class<?> clazz, String format, Object arg1, Object arg2) {
        if (isErrorEnabled(clazz)) {
            getLogger(clazz).error(format, arg1, arg2);
        }
    }

    public static void e(Class<?> clazz, String format, Object... arguments) {
        if (isErrorEnabled(clazz)) {
            getLogger(clazz).error(format, arguments);
        }
    }

    public static void e(Class<?> clazz, String msg, Throwable t) {
        if (isErrorEnabled(clazz)) {
            getLogger(clazz).error(msg, t);
        }
    }

}
