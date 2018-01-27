package com.jucaifu.common.exceptions.general;

import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.exceptions.BaseException;

/**
 * GeneralException
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class GeneralException extends BaseException {

    private GeneralExceptionInfo exceptionInfo;

    /**
     * Instantiates a new General exception.
     *
     * @param exceptionInfo the exception info
     */
    public GeneralException(GeneralExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    /**
     * 提供给框架内置的异常使用
     * <p>
     * Instantiates a new General exception.
     *
     * @param type the type
     */
    public GeneralException(GeneralExceptionType type) {
        this.exceptionInfo = new GeneralExceptionInfo(type);
    }

    /**
     * 提供给模块自定义的异常--可以指定异常级别。
     * <p>
     * Instantiates a new General exception.
     *
     * @param levenEnum the leven enum
     * @param code      the code
     * @param message   the message
     */
    public GeneralException(EnumExceptionLevel levenEnum, String code, String message) {
        this.exceptionInfo = new GeneralExceptionInfo(levenEnum, code, message);
    }

    /**
     * 提供给模块自定义的异常--FAIL级别。
     * <p>
     * Instantiates a new General exception.
     *
     * @param code    the code
     * @param message the message
     */
    public GeneralException(String code, String message) {
        this.exceptionInfo = new GeneralExceptionInfo(code, message);
    }

    /**
     * 提供给未知的系统异常。
     * <p>
     * Instantiates a new General exception.
     *
     * @param message the message
     */
    public GeneralException(String message) {
        this.exceptionInfo = new GeneralExceptionInfo(message);
    }

    /**
     * Gets exception info.
     *
     * @return the exception info
     */
    public GeneralExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * Sets exception info.
     *
     * @param exceptionInfo the exception info
     */
    public void setExceptionInfo(GeneralExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return exceptionInfo.getCode();
    }


    public String getMessage() {
        return exceptionInfo.getMessage();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public GeneralExceptionType getType() {
        return exceptionInfo.getType();
    }

}
