package com.otc.common.api.exceptions.global;

import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.exceptions.BaseExceptionInfo;
import com.jucaifu.common.exceptions.general.GeneralExceptionInfo;
import com.jucaifu.common.exceptions.general.GeneralExceptionType;
import com.jucaifu.common.util.ValueHelper;

/**
 * ErrorResponse
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class ErrorResponse extends BaseExceptionInfo {

    private Integer level;

    /**
     * Instantiates a new Error response.
     *
     * @param level the level
     * @param code the code
     * @param message the message
     */
    public ErrorResponse(Integer level, String code, String message) {
        this.level = level;
        this.code = code;
        this.message = message;
    }

    /**
     * Instantiates a new Error response.
     *
     * @param exceptionInfo the exception info
     */
    public ErrorResponse(GeneralExceptionInfo exceptionInfo) {

        EnumExceptionLevel levelEnum = exceptionInfo.getLevelEnum();
        if (levelEnum != null) {
            this.level = levelEnum.getValue();
        }

        this.code = exceptionInfo.getCode();
        this.message = exceptionInfo.getMessage();
    }


    /**
     * Instantiates a new Error response.
     *
     * @param code the code
     * @param message the message
     */
    public ErrorResponse(String code, String message) {
        this(null, code, message);
    }

    /**
     * Instantiates a new Error response.提供给未知的异常使用:99999
     *
     * @param message the message
     */
    public ErrorResponse(String message) {
        this(
                GeneralExceptionType.UNKNOWN_EXCEPTION.getValue(),
                (ValueHelper.checkStringIsEmpty(message) ? GeneralExceptionType.UNKNOWN_EXCEPTION.getDesc() : message)
        );
    }

    /**
     * 根据类型构建框架异常信息
     * <p>
     * Instantiates a new Error response.
     *
     * @param exceptionType the exception type
     * @param ex the ex
     */
    public ErrorResponse(GeneralExceptionType exceptionType, Exception ex) {

        this(exceptionType.getValue(), exceptionType.getDesc());
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}
