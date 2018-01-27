package com.jucaifu.common.exceptions;

import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.exceptions.general.GeneralException;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.util.ResourcesHelper;

/**
 * ExceptionHelper
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/9.
 */
public class ExceptionHelper {

    /**
     * Throw exception.抛出框架定制的内部异常
     *
     * @param type the type
     * @throws GeneralException the general exception
     */
    public static void throwException(GeneralExceptionType type) throws GeneralException {
        throw new GeneralException(type);
    }

    /**
     * 提供给模块自定义的异常--可以指定异常级别。
     * <p>
     * Throw exception.
     *
     * @param levenEnum the leven enum
     * @param code      the code
     * @param message   the message
     * @throws GeneralException the general exception
     */
    public static void throwException(EnumExceptionLevel levenEnum, String code, String message) throws GeneralException {
        throw new GeneralException(levenEnum, code, message);
    }

    /**
     * 提供给模块自定义的异常--FAIL级别。
     * <p>
     * Throw exception.
     *
     * @param code    the code
     * @param message the message
     * @throws GeneralException the general exception
     */
    public static void throwException(String code, String message) throws GeneralException {
        throw new GeneralException(code, message);
    }

    /**
     * Throw exception.抛出未知的错误异常
     *
     * @param message the message
     * @throws GeneralException the general exception
     */
    public static void throwException(String message) throws GeneralException {
        throw new GeneralException(message);
    }


    /**
     * Throw module exception.
     *
     * @param fileName  the file name
     * @param code      the code
     * @param arguments the arguments
     * @throws GeneralException the general exception
     */
    public static void throwModuleException(String fileName, String code, Object... arguments) throws GeneralException {

        String message = ResourcesHelper.getValue(fileName, code, arguments);

        throwException(code, message);
    }


    /**
     * Throw refuse exception.
     *
     * @throws RefuseException the refuse exception
     */
    public static void throwRefuseException() throws RefuseException {
        throw new RefuseException();
    }

}

