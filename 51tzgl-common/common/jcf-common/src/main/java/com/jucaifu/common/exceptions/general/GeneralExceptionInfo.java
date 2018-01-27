package com.jucaifu.common.exceptions.general;

import com.jucaifu.common.enums.EnumExceptionLevel;
import com.jucaifu.common.exceptions.BaseExceptionInfo;
import com.jucaifu.common.util.ValueHelper;

/**
 * GeneralExceptionInfo
 *
 * @author scofieldcai
 * @message Created by scofieldcai-dev on 15/9/7.
 */
public class GeneralExceptionInfo extends BaseExceptionInfo {

    private GeneralExceptionType type;

    /**
     * 提供给框架内置的异常使用
     * <p>
     * Instantiates a new General exception info.
     *
     * @param type the type
     */
    public GeneralExceptionInfo(GeneralExceptionType type) {
        this.type = type;
        this.levelEnum = type.getLevelEnum();
        this.code = type.getValue();
        this.message = type.getDesc();
    }


    /**
     * Instantiates a new General exception info.
     *
     * @param type      the type
     * @param levenEnum the levenEnum
     * @param code      the code
     * @param message   the message
     */
    public GeneralExceptionInfo(GeneralExceptionType type, EnumExceptionLevel levenEnum, String code, String message) {
        this.type = type;
        this.levelEnum = levenEnum;
        this.code = code;
        this.message = message;
    }


    /**
     * 提供给模块自定义的异常--可以指定异常级别。
     * <p>
     * Instantiates a new General exception info.
     *
     * @param levenEnum the leven enum
     * @param code      the code
     * @param message   the message
     */
    public GeneralExceptionInfo(EnumExceptionLevel levenEnum, String code, String message) {
        this(GeneralExceptionType.CUSTOMER_EXCEPTION, levenEnum, code, message);
    }

    /**
     * 提供给模块自定义的异常--FAIL级别。
     * <p>
     * Instantiates a new General exception info.
     *
     * @param code    the code
     * @param message the message
     */
    public GeneralExceptionInfo(String code, String message) {
        this(GeneralExceptionType.CUSTOMER_EXCEPTION, EnumExceptionLevel.FAIL, code, message);
    }

    /**
     * 提供给未知的系统异常。
     * <p>
     * Instantiates a new General exception info.
     *
     * @param message the message
     */
    public GeneralExceptionInfo(String message) {
        this(
                GeneralExceptionType.UNKNOWN_EXCEPTION,
                EnumExceptionLevel.FAIL,
                GeneralExceptionType.UNKNOWN_EXCEPTION.getValue(),
                (ValueHelper.checkStringIsEmpty(message) ? GeneralExceptionType.UNKNOWN_EXCEPTION.getDesc() : message)
        );
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public GeneralExceptionType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(GeneralExceptionType type) {
        this.type = type;
    }
}
