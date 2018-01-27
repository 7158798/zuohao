package com.jucaifu.common.exceptions;

import java.io.Serializable;

import com.jucaifu.common.enums.EnumExceptionLevel;

/**
 * BaseExceptionInfo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class BaseExceptionInfo implements Serializable {

    /**
     * The Code.
     */
    protected String code;
    /**
     * The Message.
     */
    protected String message;
    /**
     * The Level.
     */
    protected EnumExceptionLevel levelEnum;

    /**
     * Instantiates a new Base exception info.
     *
     * @param code      the code
     * @param message   the message
     * @param levelEnum the level enum
     */
    public BaseExceptionInfo(String code, String message, EnumExceptionLevel levelEnum) {
        this.code = code;
        this.message = message;
        this.levelEnum = levelEnum;
    }

    /**
     * Instantiates a new Base exception info.
     *
     * @param code    the code
     * @param message the message
     */
    public BaseExceptionInfo(String code, String message) {
        this(code, message, null);
    }

    /**
     * Instantiates a new Base exception info.
     *
     * @param message the message
     */
    public BaseExceptionInfo(String message) {
        this(null, message, null);
    }

    /**
     * Instantiates a new Base exception info.
     */
    public BaseExceptionInfo() {
    }


    /**
     * Gets levelEnum.
     *
     * @return the levelEnum
     */
    public EnumExceptionLevel getLevelEnum() {
        return levelEnum;
    }

    /**
     * Sets levelEnum.
     *
     * @param levelEnum the levelEnum
     */
    public void setLevelEnum(EnumExceptionLevel levelEnum) {
        this.levelEnum = levelEnum;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
